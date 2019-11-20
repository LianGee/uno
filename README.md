# Uno
Uno 后端。项目管理系统

## sso
1. jdk 需要导入证书
```bash
sudo keytool -import -alias cas -keystore /Library/Java/JavaVirtualMachines/jdk-11.0.5.jdk/Contents/Home/lib/security/cacerts -file cas.cer -trustcacerts
```
  

2. 本地部署cas
- 支持http
```
cas.tgc.secure=false
cas.serviceRegistry.initFromJson=true
```
```json
{
  "@class": "org.apereo.cas.services.RegexRegisteredService",
  "serviceId": "^(http|https|imaps)://.*",
  "name": "HTTPS and IMAPS",
  "id": 10000001,
  "description": "This service definition authorizes all application urls that support HTTPS and IMAPS protocols.",
  "evaluationOrder": 10000
}
```
