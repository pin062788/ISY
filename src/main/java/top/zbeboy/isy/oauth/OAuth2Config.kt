package top.zbeboy.isy.oauth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import top.zbeboy.isy.security.MyUserDetailsServiceImpl
import javax.sql.DataSource

/**
 * Created by zbeboy 2017-11-02 .
 * Oauth2 config.
 **/
@Configuration
@EnableAuthorizationServer
open class OAuth2Config  : AuthorizationServerConfigurerAdapter(){

    @Qualifier("dataSource")
    @Autowired
    private val dataSource: DataSource? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Autowired
    private val myUserDetailsService: MyUserDetailsServiceImpl? = null

    @Bean
    open fun tokenStore(): JdbcTokenStore {
        return JdbcTokenStore(this.dataSource!!)
    }

    @Bean
    open protected fun authorizationCodeServices(): AuthorizationCodeServices {
        return JdbcAuthorizationCodeServices(this.dataSource!!)
    }

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security!!.passwordEncoder(passwordEncoder).allowFormAuthenticationForClients()
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!.userDetailsService(myUserDetailsService).authorizationCodeServices(authorizationCodeServices())
                .authenticationManager(authenticationManager).tokenStore(tokenStore())
                .approvalStoreDisabled()
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        // @formatter:off
        clients!!.jdbc(dataSource)
                .passwordEncoder(passwordEncoder)
                .withClient("isy-base-client")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds(OAuth2ResourceIdsBook.ISY_BASE_RESOURCE)
                .accessTokenValiditySeconds(180 * 24 * 60 * 60)
                .refreshTokenValiditySeconds(365 * 24 * 60 * 60)
                .secret("9afcd2264ace49a09053eae4790fc812")
        // @formatter:on
    }
}