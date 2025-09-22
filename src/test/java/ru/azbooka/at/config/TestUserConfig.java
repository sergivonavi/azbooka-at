package ru.azbooka.at.config;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.LoadType.MERGE;

@Config.LoadPolicy(MERGE)
@Config.Sources({
        "system:properties",
        "file:config/user.properties"
})
public interface TestUserConfig extends Config {

    @Key("test.email")
    String email();

    @Key("test.password")
    String password();
}
