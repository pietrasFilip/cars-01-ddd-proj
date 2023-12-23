package com.app.infrastructure.api.routing;

import com.app.application.dto.response.ResponseDto;
import com.app.application.service.cars.CarsService;
import com.app.application.service.cars.provider.CarsProvider;
import com.app.application.service.cars_statistics.NumbersStatisticsType;
import com.app.infrastructure.api.transformer.JsonTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.app.domain.cars_management.model.ComponentComparator.byComponentName;
import static java.lang.Integer.parseInt;
import static spark.Spark.get;
import static spark.Spark.path;

@Component
@RequiredArgsConstructor
public class CarsRouting {
    private final CarsService carsService;
    private final JsonTransformer jsonTransformer;
    private final CarsProvider carsProvider;

    public void routes() {
        path("/is_auth", () -> {

            path("/cars", () -> {

                path("/mileage", () -> {
                    // http://localhost:8080/is_auth/cars/mileage/mileageFrom
                    get(
                            "/:mileageFrom",
                            (request, response) -> {
                                var mileageFrom = parseInt(request.params("mileageFrom"));
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService
                                        .getCarsWithMileageGreaterThan(mileageFrom));
                            },
                            jsonTransformer
                    );
                });

                path("/colors", () -> {
                    // http://localhost:8080/is_auth/cars/colors
                    get(
                            "",
                            (request, response) -> {
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService.getNumberOfCarsWithColors());
                            },
                            jsonTransformer
                    );
                });

                path("/price", () -> {
                    // http://localhost:8080/is_auth/cars/price/max
                    get(
                            "/max",
                            (request, response) -> {
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService.getMaxPriceCars());
                            },
                            jsonTransformer
                    );
                });

                path("/statistics", () -> {
                    // http://localhost:8080/is_auth/cars/statistics/type
                    get(
                            "/:type",
                            (request, response) -> {
                                var type = NumbersStatisticsType.valueOf(request.params("type").toUpperCase());
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService.getCarNumberStatistics(type));
                            },
                            jsonTransformer
                    );
                });

                path("/most_expensive", () -> {
                    // http://localhost:8080/is_auth/cars/most_expensive
                    get(
                            "",
                            (request, response) -> {
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService.getMostExpensiveCar());
                            },
                            jsonTransformer
                    );
                });

                path("/price", () -> {
                    // http://localhost:8080/is_auth/cars/price/from/to
                    get(
                            "/:from/:to",
                            ((request, response) -> {
                                var priceFrom = new BigDecimal(request.params("from"));
                                var priceTo = new BigDecimal(request.params("to"));
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService.getCarsWithPriceBetween(priceFrom, priceTo));
                            }),
                            jsonTransformer
                    );
                });
            });

        });

        path("/admin", () -> {

            path("/cars", () -> {

                path("/components/sort/alphabetically", () -> {
                    // http://localhost:8080/admin/cars/components/sort/alphabetically/asc
                    get(
                            "/asc",
                            ((request, response) -> {
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService
                                        .sortComponentsAlphabetically(byComponentName));
                            }),
                            jsonTransformer
                    );
                    // http://localhost:8080/admin/cars/components/sort/alphabetically/desc
                    get(
                            "/desc",
                            ((request, response) -> {
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService
                                        .sortComponentsAlphabetically(byComponentName.reversed()));
                            }),
                            jsonTransformer
                    );
                });

                path("/quantity", () -> {
                    // http://localhost:8080/admin/cars/quantity
                    get(
                            "",
                            ((request, response) -> {
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return new ResponseDto<>(carsService.getNumberOfCarsWithComponent());
                            }),
                            jsonTransformer
                    );
                });
            });
        });

        path("/all", () -> {

            // http://localhost:8080/all/cars
            get(
                    "/cars",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return new ResponseDto<>(carsProvider.provide());
                    },
                    jsonTransformer
            );
        });

    }
}
