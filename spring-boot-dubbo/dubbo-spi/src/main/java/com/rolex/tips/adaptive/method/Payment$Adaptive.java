package com.rolex.tips.adaptive.method;
import org.apache.dubbo.common.extension.ExtensionLoader;
public class Payment$Adaptive implements com.rolex.tips.adaptive.method.Payment {
    public void adaptivePay(org.apache.dubbo.common.URL arg0)  {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("payType", "weChatPay");
        if(extName == null) throw new IllegalStateException("Failed to get extension (com.rolex.tips.adaptive.method.Payment) name from url (" + url.toString() + ") use keys([payType])");
        com.rolex.tips.adaptive.method.Payment extension = (com.rolex.tips.adaptive.method.Payment)ExtensionLoader.getExtensionLoader(com.rolex.tips.adaptive.method.Payment.class).getExtension(extName);
        extension.adaptivePay(arg0);
    }
    public void unadaptivePay()  {
        throw new UnsupportedOperationException("The method public abstract void com.rolex.tips.adaptive.method.Payment.unadaptivePay() of interface com.rolex.tips.adaptive.method.Payment is not adaptive method!");
    }
}