package com.chenq.jira.plugin.api.ao.upgrades;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.external.ActiveObjectsUpgradeTask;
import com.atlassian.activeobjects.external.ModelVersion;
import lombok.extern.log4j.Log4j;

/**
 * @Description
 * @Date 2019/4/13 10:05
 * @Created by chenq
 */
@Log4j
public abstract class AbstractUpgradeTask implements ActiveObjectsUpgradeTask
{
    protected ActiveObjects ao;

    @Override
    public final ModelVersion getModelVersion()
    {
        return ModelVersion.valueOf(String.valueOf(getVersion()));
    }

    @Override
    public final void upgrade(ModelVersion currentVersion, ActiveObjects ao)
    {
        this.ao = ao;
        log.info(String.format("Running upgrade task for model #{%s}, ao {%s}", getModelVersion(), ao));
        doUpgrade();
    }

    protected abstract int getVersion();

    protected abstract void doUpgrade();
}