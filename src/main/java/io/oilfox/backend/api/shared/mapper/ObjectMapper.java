package io.oilfox.backend.api.shared.mapper;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Optional;

@Singleton
public class ObjectMapper {
    private ArrayList<ObjectMapping> mappings = new ArrayList<>();

    public ObjectMapping[] getMappings() {
        return mappings.toArray(new ObjectMapping[mappings.size()]);
    }

    public <S, D> void addMapping(Class<S> from, Class<D> to, ObjectConverter<S> converter) {
        ObjectMapping map = new ObjectMapping();
        map.fromType = from.getTypeName();
        map.toType = to.getTypeName();
        map.converter = converter;

        mappings.add(map);
    }

    public void clear() {
        mappings.clear();
    }

    public <T> T map(Object obj, Class<T> to) {
        String fromToken = obj.getClass().getTypeName();
        String toToken = to.getTypeName();

        Optional<ObjectMapping> result = mappings
                .stream()
                .filter(x -> x.fromType.equalsIgnoreCase(fromToken) && x.toType.equalsIgnoreCase(toToken))
                .findFirst();

        if (!result.isPresent()) {
            throw new RuntimeException("Cannot find mapping from " + fromToken + " to " + toToken);
        }

        return (T)result.get().converter.convert(obj);
    }
}
