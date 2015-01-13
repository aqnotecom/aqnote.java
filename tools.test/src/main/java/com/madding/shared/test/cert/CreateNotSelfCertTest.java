package com.madding.shared.test.cert;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.SignatureException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.Date;
//
//import org.junit.Test;
//
//import sun.misc.BASE64Encoder;
//import sun.security.util.ObjectIdentifier;
//import sun.security.x509.CertAndKeyGen;
//import sun.security.x509.CertificateExtensions;
//import sun.security.x509.CertificateSerialNumber;
//import sun.security.x509.CertificateValidity;
//import sun.security.x509.CertificateVersion;
//import sun.security.x509.Extension;
//import sun.security.x509.X500Name;
//import sun.security.x509.X509CertImpl;
//import sun.security.x509.X509CertInfo;

/**
 * 生成非自签的cert
 * 
 * @author madding.lip
 */
public class CreateNotSelfCertTest {

//    @Test
//    public void create() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException,
//                        CertificateException, SignatureException {
//
//        /***** 1.基本信息 ****/
//        // 参数分别为 公钥算法 签名算法 providername（因为不知道确切的 只好使用null 既使用默认的provider）
//        CertAndKeyGen cakGen = new CertAndKeyGen("RSA", "MD5WithRSA", null);
//
//        // 生成一对key 参数为key的长度 对于rsa不能小于512
//        cakGen.generate(1024);
//
//        // 设置subject域
//        X500Name subject = new X500Name("Email=emailxxxxx,S=SSSSSSSSS,T=TTTTTTT,UID=dmakw3398s82jd73(deviceId),CN=*.alibaba-inc.com,O=Taobao(China) Software Co.,L=Hangzhou,ST=Zhejiang,C=CN");
//        // 后一个long型参数代表从现在开始的有效期 单位为秒（如果不想从现在开始算 可以在后面改这个域）
//        X509Certificate certificate = cakGen.getSelfCertificate(subject, 10 * 60);
//
//        byte certbytes[] = certificate.getEncoded();
//        X509CertImpl x509certimpl = new X509CertImpl(certbytes);
//        X509CertInfo x509certinfo = (X509CertInfo) x509certimpl.get("x509.info");
//
//        // 设置issuer域
//        X500Name issuer = new X500Name("CN=VeriSign Class 3 Secure Server CA - G3,O=VeriSign Inc.");
//        x509certinfo.set(X509CertInfo.ISSUER, issuer);
//
//        // validity为有效时间长度 单位为秒
//        int validity = 365;
//        Date bdate = new Date();
//        Date edate = new Date();
//        edate.setTime(bdate.getTime() + validity * 1000L * 24L * 60L * 60L);
//
//        // 设置有效期域（包含开始时间和到期时间）域名等同与x509certinfo.VALIDITY
//        CertificateValidity certificatevalidity = new CertificateValidity(bdate, edate);
//        x509certinfo.set(X509CertInfo.VALIDITY, certificatevalidity);
//
//        // 设置序列号域
//        Date date = new Date();
//        x509certinfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber((int) (date.getTime() / 1000L)));
//
//        // 设置版本号 只有v1 ,v2,v3这几个合法值
//        // 如果要添加用户扩展信息 则比较麻烦 首先要确定version必须是v3否则不行
//        CertificateVersion cv = new CertificateVersion(CertificateVersion.V3);
//        x509certinfo.set(X509CertInfo.VERSION, cv);
//
//        /************************** 扩展属性 deviceId *******************************/
//        // 生成扩展域的id 是个int数组 第1位最大2 第2位最大39 最多可以几位不明....
//        ObjectIdentifier deviceIdOID = new ObjectIdentifier(new int[] { 1, 1, 0 });
//        // 其中内容的格式比较怪异 第一位是flag 这里取4暂时没出错 估计用来说明数据的用处的 第2位是后面的实际数据的长度，然后就是数据
//        String deviceId = "4ba6241cf0740c101871d078fb76521d";
//        int deviceIdLen = deviceId.length();
//        byte[] deviceIdByte = new byte[deviceIdLen + 2];
//        deviceIdByte[0] = 0x04;
//        deviceIdByte[1] = (byte) deviceIdLen; // 数据总长17位
//        for (int i = 2; i < deviceIdByte.length; i++) {
//            deviceIdByte[i] = (byte) deviceId.charAt(i - 2);
//        }
//        // 生成一个extension对象 参数分别为 deviceId，是否关键扩展，byte[]型的内容值
//        Extension deviceIdExtension = new Extension(deviceIdOID, true, deviceIdByte);
//        /************************** 扩展属性 systemType *******************************/
//        ObjectIdentifier systemTypeOID = new ObjectIdentifier(new int[] { 1, 2, 0 });
//        String systemType = "ios";
//        int systemTypeLen = systemType.length();
//        byte[] systemTypeByte = new byte[systemTypeLen + 2];
//        systemTypeByte[0] = 0x04;
//        systemTypeByte[1] = (byte) systemTypeLen; // 数据总长17位
//        for (int i = 2; i < systemTypeByte.length; i++) {
//            systemTypeByte[i] = (byte) systemType.charAt(i - 2);
//        }
//        // 生成一个extension对象 参数分别为 deviceId，是否关键扩展，byte[]型的内容值
//        Extension systemTypeExtension = new Extension(systemTypeOID, true, systemTypeByte);
//        /************************** 设置扩展属性集合 *******************************/
//        // 如果有多个extension则都放入CertificateExtensions 类中，
//        CertificateExtensions exts = new CertificateExtensions();
//        exts.set("deviceId", deviceIdExtension);
//        exts.set("systemType", systemTypeExtension);
//        // 设置extensions域
//        x509certinfo.set(X509CertInfo.EXTENSIONS, exts);
//
//        // 生成cert文件 base64加密 当然也可以不加密
//        BASE64Encoder base64 = new BASE64Encoder();
//        base64 = new BASE64Encoder();
//        FileOutputStream fos = new FileOutputStream(new File("/home/madding/output/no_self_cert.crt"));
//
//        // 使用另一个证书的私钥来签名此证书 这里使用md5散列 用rsa来加密
//        X509CertImpl x509certimplOther = new X509CertImpl(x509certinfo);
//        x509certimplOther.sign(cakGen.getPrivateKey(), "MD5WithRSA");
//        base64.encodeBuffer(x509certimplOther.getEncoded(), fos);
//        // 使用某个证书的公钥验证证书 如果验证不通过 则会抛错
//        x509certimplOther.verify(cakGen.getPublicKey(), null);
//    }

}
