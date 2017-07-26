# Spring-Oauth
## Spring Security dengan Oauth
- generate project, go to [start.spring.io](https://start.spring.io/).
- Generate Maven Project with Java and Spring Boot 1.5.4
```
Project Metadata
Group         : com.ipung.training.oauth
Artifact      : demo-spring-oauth
dependencies  : Web, Thymeleaf yang lain menyusul
Java Version  : 1.8
```
-Ekstrak, lalu import project ke eclipse
-Buat class HalloController didalam package com.ipung.training.oauth.controller
```
@Controller
public class HalloController {

	@RequestMapping(value="/halo")
	public void halo(Model m){
		m.addAttribute("waktu", new Date());
	}
}
```
buat file halo.html didalam src/main/resources/templates, secara default Thymeleaf mencari source html dari package tsb.
<body>
	<h1>Halo Spring Boot</h1>
	<h2>Waktu saat ini : <span th:text="${waktu}">waktu</span></h2>
</body>

Running Project, lalu akses url http://localhost:8080/halo.

Tambahkan depedency ke dalam pom.xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
    
 Running project, lalu akses url http://localhost:8080/halo, maka browser akan mengeluarkan basic authentication.
 user = user
 password = di generate di console.
 basic auth tidak memiliki logout.
 
 buat class KonfigurasiSecurity di dalam package package com.ipung.training.oauth.config
 @Configuration
public class KonfigurasiSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication()
		.withUser("ipung")
		.password("123")
		.roles("USER");
	}
  
buat file login.html
Untuk login.html, saya copy-paste dari https://getbootstrap.com/examples/signin/ , begitu juga dengan css nya.
tinggal disesuaikan, 
<title>Insert title here</title>
	
	<!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous"/>

	 <!-- Custom styles for this template -->
    <link type="text/css" href="css/aplikasi.css" rel="stylesheet" />
</head>
<body>

	<div class="container">

      <form class="form-signin" th:action="@{/login}" method="post">
      	<div th:if="${param.error}" class="alert alert-error">invalid username and password.</div>
      	<div th:if="${param.logout}" class="alert alert-succes">you have been logout.</div>
      	
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username"/>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="true" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    </div> 
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>

Note : 
- untuk file css, letakan didalam src/main/resources/static/css 
- didalam label html jangan lupa untuk menambahkan name="username" dan name="password", karena KonfigurasiSecurity membaca variabel tsb.


tambahkan method HttpSecurity, jika file login.html tidak terbaca.
package com.ipung.training.oauth.config;

@Configuration
public class KonfigurasiSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication()
		.withUser("ipung")
		.password("123")
		.roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login")
		.permitAll();
	}
	
}

tambahkan di application.properties, agar bisa me-reload perubahan di src/main/templates on-the-fly di sisi browser .
spring.thymeleaf.cache=false

untuk mengaktifkan logout dan defaultSuccesUrl, tambahkan konfigurasi seperti di bawah :
@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login")
		.permitAll()
		.defaultSuccessUrl("/halo")
		.and().logout();
		;
	}

tambahkan function logout pada halo.html
<body>
	<form name="f" th:action="@{/logout}" method="post">
		<input type="submit" value="Logout"/>
	</form>

	<h1>Halo Spring Boot</h1>
	<h2>Waktu saat ini : <span th:text="${waktu}">waktu</span></h2>
  ...
</body>

<h1>Konfigurasi Security melalui database<h1>
tambahkan depedency ke pom.xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

tambahkan settingan konfigurasi ke application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/oauth?useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

buat database oauth
untuk schema tabel telah di tentukan oleh Spring, bisa merujuk ke
https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#appendix-schema

buat folder src/main/sql dan buat file skema-security-sql.sql, ini hanya untuk dokumentasi pribadi saja. Optional.

