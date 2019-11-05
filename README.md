# [CRUD operation using ElasticSearch] below technologies
Java
Spring-boot ,Spring-Security
MySql (for rest authentication & insert data to generate id)
JPA
download elastic search form https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.8.3.zip

# create index using postman 
Method:PUT URL: http://localhost:9200/employee-data // {employee-data} is index name 

# use below services for CRUD Operation
# 1. Add Method:POST 
http://localhost:8080/spring-boot-elastic-search/employee/add
{
	"lastName": "ooasssssss",
	"firstName": "aaaaaaaa",
	"email":"sssaa@gmail.com"
} 
# 2. update Method:POST 
http://localhost:8080/spring-boot-elastic-search/employee/update/2
{
	"lastName": "ooasssssss",
	"firstName": "aaaaaaaa",
	"email":"sssaa@gmail.com"
} 
# 3. Delete Method:GET/POST 
http://localhost:8080/spring-boot-elastic-search/employee/delete/2
# 4. Get By Id Method:GET/POST 
http://localhost:8080/spring-boot-elastic-search/employee/get/2
# 4. Get All Method:GET/POST 
http://localhost:8080/spring-boot-elastic-search/employee/geAll

 
