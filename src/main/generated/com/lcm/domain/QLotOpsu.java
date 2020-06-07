package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLotOpsu is a Querydsl query type for LotOpsu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLotOpsu extends EntityPathBase<LotOpsu> {

    private static final long serialVersionUID = -1051005873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLotOpsu lotOpsu = new QLotOpsu("lotOpsu");

    public final NumberPath<Integer> actualQty = createNumber("actualQty", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final QLotOpsuId lotOpsuId;

    public QLotOpsu(String variable) {
        this(LotOpsu.class, forVariable(variable), INITS);
    }

    public QLotOpsu(Path<? extends LotOpsu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLotOpsu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLotOpsu(PathMetadata metadata, PathInits inits) {
        this(LotOpsu.class, metadata, inits);
    }

    public QLotOpsu(Class<? extends LotOpsu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lotOpsuId = inits.isInitialized("lotOpsuId") ? new QLotOpsuId(forProperty("lotOpsuId")) : null;
    }

}

