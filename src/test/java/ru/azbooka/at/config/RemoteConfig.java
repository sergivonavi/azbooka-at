package ru.azbooka.at.config;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.LoadType.MERGE;

@Config.LoadPolicy(MERGE)
@Config.Sources({
        "system:properties",
        "file:config/remote.properties"
})
public interface RemoteConfig extends Config {

    @Key("remote.useRemote")
    boolean isRemote();

    @Key("remote.domain")
    String domain();

    @Key("remote.username")
    String username();

    @Key("remote.password")
    String password();
}
