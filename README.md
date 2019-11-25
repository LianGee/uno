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

## 文档
文档目录存储在mysql中，内容存储在es中

### es 初始化
- 创建索引
```
curl -X PUT "localhost:9200/uno_doc"

{"acknowledged":true,"shards_acknowledged":true,"index":"uno_doc"}
```
- 创建索引的mapping
```
# 注意，这里的test_type与url上的test_type名保存一致
curl -X PUT 'localhost:9200/uno_doc/_mapping/doc' -d ' 
{
  "doc": {
      "properties": {
        "id": {
          "type": "long",
          "index": "not_analyzed"
        },
        "title": {
          "type": "string",
        },
        "content": {
          "type": "string",
        },
        "authorName": {
          "type": "string",
          "index": "not_analyzed"
        },
        "projectId": {
          "type": "long",
          "index": "not_analyzed"
        },
        "catalogueId": {
          "type": "long",
          "index": "not_analyzed"
        }
      }
    }
  }'
```
