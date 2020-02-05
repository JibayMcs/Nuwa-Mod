package fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.base;

import fr.zeamateis.nuwa.contentpack.common.json.data.events.processes.condition.base.ICondition;

import java.util.ArrayList;
import java.util.List;

public interface IProcess {

    List<ICondition> conditions = new ArrayList<>();

    String getRegistryName();
}
