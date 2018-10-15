package uk.ac.reigate.config.testing;


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 *
 * Integration testing specific configuration - creates a in-memory datasource,
 * sets hibernate on create drop mode and inserts some test data on the database.
 *
 * This allows to clone the project repository and start a running application with the command
 *
 * mvn clean install tomcat7:run-war -Dspring.profiles.active=test
 *
 * Access http://localhost:8080/ and login with test123 / Password2, in order to see some test data,
 * or create a new user.
 *
 */
@Configuration
@Profile("test")
@EnableJpaRepositories(basePackages="uk.ac.reigate.repositories")
@EnableTransactionManagement
@ComponentScan([
    "uk.ac.reigate.repositories",
    "uk.ac.reigate.init"
])
public class TestDatasourceConfiguration {
    
    @Bean(initMethod = "init")
    public TestDataInitializer initTestData() {
        return new TestDataInitializer();
    }
    
    @Bean(name = "datasource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test_cid_template;MVCC=true;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("password");
        return dataSource;
    }
    
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("uk.ac.reigate.querydsl");
        entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        
        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        //        jpaProperties.put("hibernate.use_sql_comments", "true");
        jpaProperties.put("naming-strategy", "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy");
        entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
        
        return entityManagerFactoryBean;
    }
}

