双向认证

服务器端的信息生成

1.生成服务器端私钥，导入到服务器端的keystore文件中
> keytool -genkey -alias serverkey -keystore kserver.keystore

2.根据私钥导出服务器端证书
> keytool -export -alias serverkey -keystore kserver.keystore -file server.crt

3.将服务器端证书导入到客户端的trust keystore中
> keytool -import -alias serverkey -file server.crt -keystore tclient.keystore


客户端的信息生成

1.生成客户端私钥，导入到客户端的keystore文件中
> keytool -genkey -alias clientkey -keystore kclient.keystore

2.根据私钥到处客户端证书
>  keytool -export -alias clientkey -keystore kclient.keystore -file client.crt

3.将客户端证书导入到服务器端的trust keystore中
> keytool -import -alias clientkey -file client.crt -keystore tserver.keystore


服务端和客户端之间的基于身份认证的交互
client采用kclient.keystore中的clientkey私钥进行数据加密，发送给server。
server采用tserver.keystore中的client.crt证书（包含了clientkey的公钥）对数据解密，
如果解密成功，证明消息来自client，进行逻辑处理。
server采用kserver.keystore中的serverkey私钥进行数据加密，发送给client。
client采用tclient.keystore中的server.crt证书（包含了serverkey的公钥）对数据解密，
如果解密成功，证明消息来自server，进行逻辑处理。
如果过程中，解密失败，那么证明消息来源错误。不进行逻辑处理。
