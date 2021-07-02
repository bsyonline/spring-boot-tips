package com.rolex.tips.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Component
public class MessageUtils {
    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    /**
     * 获取国际化翻译值
     */
    public static String get(String msgKey, String... value) {
        try {
            String message = messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
            MessageFormat messageFormat = new MessageFormat(message);
            String format = messageFormat.format(value);
            return format;
        } catch (Exception e) {
            return msgKey;
        }
    }

    public static String getMsg(String key, String... value) {
        Locale locale1 = new Locale("en_US");
        Locale locale2 = Locale.getDefault(); //获取默认语言地区
        System.out.println(locale2.getCountry()); //CN
        System.out.println(locale2.getDisplayCountry()); //中国
        System.out.println(locale2.getDisplayLanguage()); //中文
        System.out.println(locale2.getDisplayName()); //中文(中国)
        System.out.println(locale2.getDisplayLanguage(locale1)); //以某种语言显示语言,这里是Chinese
        System.out.println(locale2.getLanguage()); //zh 语言代表符
        System.out.println(locale2.toLanguageTag()); //语言标签 格式语言-国家 这里是zh-CN
        //Locale自定义了很多语言跟国家常量 如中国 和中文,德国和德文
        Locale china = Locale.CHINA; // zh-Cn
        Locale chinese = Locale.CHINESE; //ZH
        System.out.println(china.toLanguageTag()); //ZH-CN
        System.out.println(chinese.toLanguageTag()); //ZH
        Locale german = Locale.GERMAN; //de
        Locale germany = Locale.GERMANY; //de-DE
        System.out.println(german.toLanguageTag());//de
        System.out.println(germany.toLanguageTag());//de-DE
        Locale[] locales = Locale.getAvailableLocales();//获取世界可用的地区
        for (Locale locale : locales) {
            System.out.println(locale.toLanguageTag());
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale2);
        String s = resourceBundle.getString(key);
        MessageFormat messageFormat = new MessageFormat(s);
        String format = messageFormat.format(value);
        System.out.println(format);

        return format;
    }
}
