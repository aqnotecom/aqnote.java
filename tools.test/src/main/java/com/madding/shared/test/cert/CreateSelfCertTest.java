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
//
//import org.junit.Test;
//
//import sun.misc.BASE64Encoder;
//import sun.security.x509.CertAndKeyGen;
//import sun.security.x509.X500Name;

/**
 * 生成自签的cert
 * 
 * @author madding.lip
 */
public class CreateSelfCertTest {

//    @Test
//    public void create() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException,
//                        CertificateException, SignatureException {
//
//        // 参数分别为 公钥算法 签名算法 providername（因为不知道确切的 只好使用null 既使用默认的provider）
//        CertAndKeyGen cakGen = new CertAndKeyGen("RSA", "MD5WithRSA", null);
//
//        // 生成一对key 参数为key的长度 对于rsa不能小于512
//        cakGen.generate(1024);
//
//        // 设置subject域
//        X500Name subject = new X500Name("CN=*.alibaba-inc.com,O=TAOBAO");
//
//        // 后一个long型参数代表从现在开始的有效期 单位为秒（如果不想从现在开始算 可以在后面改这个域）
//        X509Certificate certificate = cakGen.getSelfCertificate(subject, 10);
//
//        // 生成cert文件 base64加密 当然也可以不加密
//        BASE64Encoder base64 = new BASE64Encoder();
//        FileOutputStream fos = new FileOutputStream(new File("/home/madding/output/self_cert.crt"));
//        base64.encodeBuffer(certificate.getEncoded(), fos);
//
//    }
}
