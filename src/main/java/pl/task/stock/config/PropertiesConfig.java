package pl.task.stock.config;


import com.google.inject.Singleton;
import lombok.Getter;
import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


@Getter
@Singleton
public class PropertiesConfig {

    private Configuration config;

    public PropertiesConfig() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName("application.properties"));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException cex) {
            config = new SystemConfiguration();
        }

    }





}
