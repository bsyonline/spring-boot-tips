# spring boot jasypt

加密
```
java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="123456" password=abc123 algorithm=PBEWithMD5AndDES
```

解密
```
java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input="fXQH7wOsDPPPMb3QhqDVZw==" password=abc123 algorithm=PBEWithMD5AndDES
```