package org.example.testcontainer;

import org.example.config.SubtractionServiceProperties;
import org.example.testcontainer.network.INetworkService;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Service
public final class SubtractionContainerService implements IContainerService {

    private final Integer containerPort;

    private final GenericContainer<?> subtractionContainer;

    public SubtractionContainerService(INetworkService networkService,
                                       SubtractionServiceProperties subtractionServiceProperties) {

        DockerImageName subtractionServiceImage = DockerImageName
                .parse(subtractionServiceProperties.getImageName())
                .asCompatibleSubstituteFor("subtraction-service");

        this.subtractionContainer = new GenericContainer<>(subtractionServiceImage)
                .withExposedPorts(subtractionServiceProperties.getPort())
                .withNetwork(networkService.getNetwork())
                .withEnv("SERVER_PORT", subtractionServiceProperties.getPort().toString());

        this.subtractionContainer.start();

        this.containerPort = this.subtractionContainer.getMappedPort(subtractionServiceProperties.getPort());
    }

    public Integer getContainerPort() {
        return this.containerPort;
    }

    public void closeContainer() {
        this.subtractionContainer.close();
    }

    public GenericContainer<?> getSubtractionContainer() {
        return subtractionContainer;
    }
}
