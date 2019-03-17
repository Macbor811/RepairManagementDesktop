package pl.polsl.repairmanagementdesktop;

import org.springframework.core.ParameterizedTypeReference;

import java.util.HashMap;
import java.util.Map;

import pl.polsl.repairmanagementdesktop.model.*;



public class TypeReferenceMap {

    private Map<Class, ParameterizedTypeReference<?>> map = new HashMap<>();


    public TypeReferenceMap(){
        map.put(ActivityDTO.class, new ParameterizedTypeReference<ActivityDTO>(){});
        map.put(AddressDTO.class, new ParameterizedTypeReference<AddressDTO>(){});
        map.put(ActivityTypeDTO.class, new ParameterizedTypeReference<ActivityTypeDTO>(){});
        map.put(ClientDTO.class, new ParameterizedTypeReference<ClientDTO>(){});
        map.put(ItemDTO.class, new ParameterizedTypeReference<ItemDTO>(){});
        map.put(ItemTypeDTO.class, new ParameterizedTypeReference<ItemTypeDTO>(){});
        map.put(PersonnelDTO.class, new ParameterizedTypeReference<PersonnelDTO>(){});
        map.put(RequestDTO.class, new ParameterizedTypeReference<RequestDTO>(){});
    }

    public ParameterizedTypeReference<?> get(Class forClass){
        return map.get(forClass);
    }
}
