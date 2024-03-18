package es.in2.dss;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ContainerManager {

    private static final ContainerManager INSTANCE = new ContainerManager();
    private static final Network testNetwork = Network.newNetwork();
    private static final PostgreSQLContainer<?> postgresContainer;

    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("desmos")
                .withUsername("guest")
                .withPassword("guest")
                .withNetwork(testNetwork)
                .withNetworkAliases("postgres");
        postgresContainer.start();

        // todo: add Vault container
    }

    public static ContainerManager getInstance() {
        return INSTANCE;
    }

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> String.format("r2dbc:pool:postgresql://%s:%s/desmos",
                postgresContainer.getHost(),
                postgresContainer.getFirstMappedPort()));
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
        registry.add("spring.flyway.url", postgresContainer::getJdbcUrl);
    }

}
