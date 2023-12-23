package com.app.config;

import com.app.application.service.cars.CarsService;
import com.app.application.service.cars.CarsServiceImpl;
import com.app.application.service.cars.provider.CarsProvider;
import com.app.application.validator.token.AuthenticationDtoValidator;
import com.app.application.validator.token.AuthorizationDtoValidator;
import com.app.application.validator.token.RefreshTokenDtoValidator;
import com.app.application.validator.token.TokensDtoValidator;
import com.app.application.validator.token.impl.AuthenticationDtoValidatorImpl;
import com.app.application.validator.token.impl.AuthorizationDtoValidatorImpl;
import com.app.application.validator.token.impl.RefreshTokenDtoValidatorImpl;
import com.app.application.validator.token.impl.TokensDtoValidatorImpl;
import com.app.application.validator.user.CreateUserDtoValidator;
import com.app.application.validator.user.impl.CreateUserDtoValidatorImpl;
import com.app.domain.cars_management.model.repository.db.CarRepositoryDb;
import com.app.domain.cars_management.model.repository.json.CarRepositoryJson;
import com.app.domain.cars_management.model.repository.json.ComponentRepositoryJson;
import com.app.domain.cars_management.model.repository.txt.CarRepositoryTxt;
import com.app.domain.cars_management.model.repository.txt.ComponentRepositoryTxt;
import com.app.domain.cars_management.policy.factory.factory.FromDbToCarWithValidator;
import com.app.domain.cars_management.policy.factory.factory.FromJsonToCarWithValidator;
import com.app.domain.cars_management.policy.factory.factory.FromTxtToCarWithValidator;
import com.app.domain.cars_management.policy.factory.loader.car.db.CarDataDbLoader;
import com.app.domain.cars_management.policy.factory.loader.car.db.CarDataDbLoaderImpl;
import com.app.domain.cars_management.policy.factory.loader.car.json.CarDataJsonLoader;
import com.app.domain.cars_management.policy.factory.loader.car.json.CarDataJsonLoaderImpl;
import com.app.domain.cars_management.policy.factory.loader.car.txt.CarDataTxtLoader;
import com.app.domain.cars_management.policy.factory.loader.car.txt.CarDataTxtLoaderImpl;
import com.app.domain.cars_management.policy.factory.processor.CarDataProcessor;
import com.app.domain.cars_management.policy.factory.processor.impl.CarDataProcessorDbImpl;
import com.app.domain.cars_management.policy.factory.processor.impl.CarDataProcessorJsonImpl;
import com.app.domain.cars_management.policy.factory.processor.impl.CarDataProcessorTxtImpl;
import com.app.domain.cars_management.policy.factory.validator.CarDataValidator;
import com.app.domain.cars_management.policy.factory.validator.impl.CarDataValidatorImpl;
import com.app.infrastructure.persistence.repository.impl.db.CarRepositoryDbImpl;
import com.app.infrastructure.persistence.repository.impl.db.dao.CarEntityDao;
import com.app.infrastructure.persistence.repository.impl.db.dao.impl.CarEntityDaoImpl;
import com.app.infrastructure.persistence.repository.impl.json.CarRepositoryJsonImpl;
import com.app.infrastructure.persistence.repository.impl.json.ComponentRepositoryJsonImpl;
import com.app.infrastructure.persistence.repository.impl.txt.CarRepositoryTxtImpl;
import com.app.infrastructure.persistence.repository.impl.txt.ComponentRepositoryTxtImpl;
import com.app.infrastructure.persistence.repository.provider.impl.CarsProviderImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class AppTestConfig {
    private final Environment environment;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("HBN_TEST");
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }

    // -----------------------------------------------------------------------------------
    // PERSISTENCE
    // -----------------------------------------------------------------------------------

    @Bean
    public ComponentRepositoryJson componentRepositoryJson(Gson gson) {
        return new ComponentRepositoryJsonImpl(gson,
                environment.getProperty("json.components.path"));
    }

    @Bean
    public CarRepositoryJson carRepositoryJson(Gson gson, ComponentRepositoryJson componentRepositoryJson) {
        return new CarRepositoryJsonImpl(gson, environment.getProperty("json.cars.path"),
                componentRepositoryJson);
    }

    @Bean
    public ComponentRepositoryTxt componentRepositoryTxt() {
        return new ComponentRepositoryTxtImpl(environment.getRequiredProperty("txt.components.path"));
    }

    @Bean
    public CarRepositoryTxt carRepositoryTxt(ComponentRepositoryTxt componentRepositoryTxt) {
        return new CarRepositoryTxtImpl(componentRepositoryTxt,
                environment.getRequiredProperty("txt.cars.path"));
    }

    // -----------------------------------------------------------------------------------
    // SERVICE
    // -----------------------------------------------------------------------------------

    @Bean
    public FromDbToCarWithValidator fromDbToCarWithValidator(CarRepositoryDb carRepositoryDb, CarDataValidator carDataValidator) {
        return new FromDbToCarWithValidator(carRepositoryDb, carDataValidator);
    }

    @Bean
    public FromJsonToCarWithValidator fromJsonToCarWithValidator(CarRepositoryJson carRepositoryJson,
                                                                 Gson gson,
                                                                 CarDataValidator carDataValidator) {
        return new FromJsonToCarWithValidator(carRepositoryJson, gson, carDataValidator);
    }

    @Bean
    public FromTxtToCarWithValidator fromTxtToCarWithValidator(CarRepositoryTxt carRepositoryTxt,
                                                               CarDataValidator carDataValidator) {
        return new FromTxtToCarWithValidator(carRepositoryTxt, carDataValidator);
    }

    @Bean
    @Qualifier("dbProcessor")
    public CarDataProcessor carDataDbProcessor(FromDbToCarWithValidator fromDbToCarWithValidator) {
        return new CarDataProcessorDbImpl(fromDbToCarWithValidator);
    }

    @Bean
    @Qualifier("jsonProcessor")
    public CarDataProcessor carDataJsonProcessor(FromJsonToCarWithValidator fromJsonToCarWithValidator) {
        return new CarDataProcessorJsonImpl(fromJsonToCarWithValidator);
    }

    @Bean
    @Qualifier("txtProcessor")
    public CarDataProcessor carDataTxtProcessor(FromTxtToCarWithValidator fromTxtToCarWithValidator) {
        return new CarDataProcessorTxtImpl(fromTxtToCarWithValidator);
    }

    @Bean
    public CarsProvider carsProvider(@Qualifier("dbProcessor") CarDataProcessor carDataDbProcessor,
                                     @Qualifier("jsonProcessor") CarDataProcessor carDataJsonProcessor,
                                     @Qualifier("txtProcessor") CarDataProcessor carDataTxtProcessor) {
        return new CarsProviderImpl(carDataDbProcessor, carDataJsonProcessor, carDataTxtProcessor);
    }

    @Bean
    public CarsService carsService(CarsProvider carsProvider) {
        return new CarsServiceImpl(carsProvider);
    }

    @Bean
    public AuthorizationDtoValidator authorizationDtoValidator() {
        return new AuthorizationDtoValidatorImpl();
    }

    @Bean
    public RefreshTokenDtoValidator refreshTokenDtoValidator() {
        return new RefreshTokenDtoValidatorImpl();
    }

    @Bean
    public TokensDtoValidator tokensDtoValidator() {
        return new TokensDtoValidatorImpl();
    }

    @Bean
    public AuthenticationDtoValidator authenticationDtoValidator() {
        return new AuthenticationDtoValidatorImpl();
    }

    @Bean
    public CreateUserDtoValidator createUserDtoValidator() {
        return new CreateUserDtoValidatorImpl();
    }

    // -----------------------------------------------------------------------------------
    // ABSTRACT FACTORY
    // -----------------------------------------------------------------------------------

    @Bean
    public CarEntityDao carEntityDbDao(EntityManagerFactory entityManagerFactory) {
        return new CarEntityDaoImpl(entityManagerFactory);
    }

    @Bean
    public CarRepositoryDb carRepositoryDb(CarEntityDao carEntityDao) {
        return new CarRepositoryDbImpl(carEntityDao);
    }

    @Bean
    public CarDataDbLoader carDataDbLoader(CarRepositoryDb carRepositoryDb) {
        return new CarDataDbLoaderImpl(carRepositoryDb);
    }

    @Bean
    public CarDataJsonLoader carDataJsonLoader(Gson gson, CarRepositoryJson carRepositoryJson) {
        return new CarDataJsonLoaderImpl(gson, carRepositoryJson);
    }

    @Bean
    public CarDataTxtLoader carDataTxtLoader(CarRepositoryTxt carRepositoryTxt) {
        return new CarDataTxtLoaderImpl(carRepositoryTxt);
    }

    @Bean
    public CarDataValidator carDataValidator() {
        return new CarDataValidatorImpl(
                environment.getRequiredProperty("validator.regex.model.name"),
                environment.getRequiredProperty("validator.regex.items.name")
        );
    }
}
