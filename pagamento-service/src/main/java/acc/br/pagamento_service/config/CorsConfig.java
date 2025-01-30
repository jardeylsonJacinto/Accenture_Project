package acc.br.pagamento_service.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true); // Permitir credenciais como cookies e autenticação
        corsConfig.addAllowedOriginPattern("*"); // Permitir qualquer origem
        corsConfig.addAllowedHeader("*"); // Permitir qualquer cabeçalho
        corsConfig.addAllowedMethod("*"); // Permitir qualquer método (GET, POST, etc.)
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // Aplicar CORS a todos os endpoints

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0); // Prioridade do filtro
        return bean;
    }
}
