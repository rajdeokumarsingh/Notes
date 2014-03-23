
Before executing the Authentication process, Spring Security will run a filter responsible with storing the Security Context between requests – 
SecurityContextPersistenceFilter


The context will be stored according to a strategy – and by default, this is the 

HttpSessionSecurityContextRepository 
    – which uses the HTTP Session as storage.


http://docs.spring.io/spring-security/site/docs/3.0.x/reference/session-mgmt.html


http://blog.csdn.net/shadowsick/article/details/8572467
<!-- SESSION管理 -->  
<bean id="sessionRegistry"  
class="org.springframework.security.core.session.SessionRegistryImpl" />  

<bean id="concurrentSessionFilter"  
class="org.springframework.security.web.session.ConcurrentSessionFilter">  
<property name="sessionRegistry" ref="sessionRegistry" />  
<property name="expiredUrl" value="/apply/skip/restimeout.html" />  
<property name="logoutHandlers">  
<list>  
<ref local="logoutHandler" />  
</list>  
</property>  
</bean>  

<!-- 注销监听器  -->  
<bean id="logoutHandler"  
class="org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler">  
<property name="InvalidateHttpSession" value="true" />  
</bean>  


@Autowired  //利用spring注入后就可以取所有登录用户，不能加static属性，否则会取不到  
private SessionRegistry sessionRegistry; 

另使用： 
import org.springframework.security.core.context.SecurityContextHolder; 
SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 可以获取当前登录用户

http://stackoverflow.com/questions/11271449/how-can-i-have-list-of-all-users-logged-in-via-spring-secuirty-my-web-applicat
