# Allow Thymeleaf templates to be reloaded at dev time
spring.view.prefix: /WEB-INF/jsp/
spring.view.suffix: .jsp

logging.file : ./${projectName}.log

jdbc.driverClassName :  ${r"${jdbc.driverClassName}"}
jdbc.url :  ${r"${jdbc.url}"}
jdbc.username : ${r"${jdbc.username}"}
jdbc.password : ${r"${jdbc.password}"}

DEFAULT_MODULE : codemonkey-em

#OS\u542F\u52A8\u9A8C\u8BC1\u7801
OS_VERIFY_CODE : bef91365086b493427f5bd76776b833e

#\u5F00\u53D1\u6A21\u5F0F\u6807\u5FD7    dev:\u5F00\u53D1\u6A21\u5F0F    , prod:\u751F\u4EA7\u6A21\u5F0F
DEV_MODE : dev

#\u662F\u5426\u5BFC\u5165\u521D\u59CB\u5316\u6570\u636E
LOAD_INIT_DATA : true
#ajax\u8BF7\u6C42\u9ED8\u8BA4\u8D77\u59CB\u8DEF\u5F84
JSON_REQUEST_PREFIX : app/ext
#\u8C03\u7528his\u63A5\u53E3\u7C7B\u578B(P:\u5B58\u50A8\u8FC7\u7A0B,V:\u89C6\u56FE)
TARGET_VIEW_OR_PROC : V

#shiro\u8BBF\u95EE\u63A7\u5236
shiro.signupUrl : /auth/signup
shiro.loginUrl : /auth/login
shiro.ajaxLoginUrl : /auth/ajaxLogin
shiro.successUrl : /auth/home
shiro.rootUrl : /auth/home
shiro.unauthorizedUrl : /auth/unauthorized
shiro.anonUrl : /WEB-INF/decorator.jsp
shiro.authcUrl : /ext/**

#hibernate\u914D\u7F6E
hibernate.dialect : ${r"${hibernate.dialect}"}
hibernate.show_sql : ${r"${hibernate.show_sql}"}
hibernate.cache.provider_class : ${r"${hibernate.cache.provider_class}"}
hibernate.format_sql : ${r"${hibernate.format_sql}"}
hibernate.cache.use_second_level_cache : ${r"${hibernate.cache.use_second_level_cache}"}						
hibernate.cache.use_query_cache : ${r"${hibernate.cache.use_query_cache}"}
hibernate.use_sql_comments : ${r"${hibernate.use_sql_comments}"}
hibernate.validator.autoregister_listeners : ${r"${hibernate.validator.autoregister_listeners}"}
hibernate.cache.use_structured_entries : ${r"${hibernate.cache.use_structured_entries}"}			
hibernate.query.substitutions : ${r"${hibernate.query.substitutions}"}
hibernate.bytecode.use_reflection_optimizer : ${r"${hibernate.bytecode.use_reflection_optimizer}"}
hibernate.generate_statistics : ${r"${hibernate.generate_statistics}"}
hibernate.transaction.flush_before_completion : ${r"${hibernate.transaction.flush_before_completion}"}
hibernate.transaction.auto_close_session : ${r"${hibernate.transaction.auto_close_session}"}
hibernate.order_updates : ${r"${hibernate.order_updates}"}
hibernate.order_inserts : ${r"${hibernate.order_inserts}"}
hibernate.jdbc.batch_versioned_data : ${r"${hibernate.jdbc.batch_versioned_data}"}
hibernate.jdbc.batch_size : ${r"${hibernate.jdbc.batch_size}"}
hibernate.max_fetch_depth : ${r"${hibernate.max_fetch_depth}"}
hibernate.hbm2ddl.auto : ${r"${hibernate.hbm2ddl.auto}"}
hibernate.default_schema:${r"${hibernate.default_schema}"}

#hibernate audit
org.hibernate.envers.audit_table_suffix : ${r"${org.hibernate.envers.audit_table_suffix}"}
org.hibernate.envers.audit_table_prefix : ${r"${org.hibernate.envers.audit_table_prefix}"}
org.hibernate.envers.revision_field_name : ${r"${org.hibernate.envers.revision_field_name}"}
org.hibernate.envers.revision_type_field_name : ${r"${org.hibernate.envers.revision_type_field_name}"}
org.hibernate.envers.revision_on_collection_change : ${r"${org.hibernate.envers.revision_on_collection_change}"}
org.hibernate.envers.do_not_audit_optimistic_locking_field : ${r"${org.hibernate.envers.do_not_audit_optimistic_locking_field}"}
org.hibernate.envers.store_data_at_delete : ${r"${org.hibernate.envers.store_data_at_delete}"}
org.hibernate.envers.default_schema : ${r"${org.hibernate.envers.default_schema}"}
org.hibernate.envers.default_catalog : ${r"${org.hibernate.envers.default_catalog}"}

#Email
SMTP_HOST : smtp.exmail.qq.com
SMTP_PORT : 465
SMTP_USER : 
SMTP_PASSWORD : 

LOG_SESSION : false
LOG_REQUEST : true

#Sha256,md5,Sha1
PASSWORD_ENCRYPT : Sha256

spring.http.gzip.mimeTypes : application/javascript
spring.http.gzip.minGzipSize : 1024
spring.http.gzip.deflateCompressionLevel : 9

server.port : 8080

#please set DB_TYPE when use mybatis
#mysql,mssql,oracle
DB_TYPE : mysql