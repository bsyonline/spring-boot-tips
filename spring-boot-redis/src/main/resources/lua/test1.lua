redis.log(redis.LOG_WARNING, "------------------")
--获取KEY
local key1 = KEYS[1]
redis.log(redis.LOG_WARNING, "key1=" .. key1)

local params = ARGV

local from = ARGV[1]
local to = ARGV[2]
local type2 = ARGV[3]

local type1 = string.sub(type2, 2, string.len(type2)-1)

redis.log(redis.LOG_WARNING, "from=" .. from)
redis.log(redis.LOG_WARNING, "to=" .. to)
redis.log(redis.LOG_WARNING, "type=" .. type2)
redis.log(redis.LOG_WARNING, "type1=" .. type1)


for k,v in pairs(params) do
    redis.log(redis.LOG_WARNING, "kv=" .. k .. ", v=" .. v)
end

local res = redis.call("zrevrange", key1, from, to, "WITHSCORES")
local ids = {}
local array = {}

local table_len = table.getn(res)
redis.log(redis.LOG_WARNING, "res=" .. type(res))
redis.log(redis.LOG_WARNING, "pairs(res)=" .. type(pairs(res)))
redis.log(redis.LOG_WARNING, "tablen=" .. table_len)
redis.log(redis.LOG_WARNING, "table1=" .. res[1])
local y = 1
for i=1, table_len/2 do
    i = (i-1)*2+1
    local id = res[i]
    local priority = res[i+1]
    redis.log(redis.LOG_WARNING, "res:i=" .. i .. ", id=" .. id .. ", priority=" .. priority)
    local t = string.find(id, type1)
    if(t ~= nil) then
        local s = string.sub(id, string.len(type1)+2, string.len(id))
        redis.log(redis.LOG_WARNING, "s=" .. s)
        ids[y] = s
        array[id] = priority
        y = y + 1
    end
    i = i
end

for i,id in pairs(ids) do
    redis.log(redis.LOG_WARNING, "ids:i=" .. i .. ", id=" .. id)
end
for id,priority in pairs(array) do
    redis.log(redis.LOG_WARNING, "array:id=" .. id .. ", priority=" .. priority)
    redis.call("ZADD", key1.."_tmp", priority, id)
    redis.call("ZREM", key1, id)
end

return ids