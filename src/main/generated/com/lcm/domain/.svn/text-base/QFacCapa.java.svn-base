package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFacCapa is a Querydsl query type for FacCapa
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFacCapa extends EntityPathBase<FacCapa> {

    private static final long serialVersionUID = 1797026642L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFacCapa facCapa = new QFacCapa("facCapa");

    public final QFacCapaId facCapaId;

    public final StringPath itemValue = createString("itemValue");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public QFacCapa(String variable) {
        this(FacCapa.class, forVariable(variable), INITS);
    }

    public QFacCapa(Path<? extends FacCapa> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFacCapa(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFacCapa(PathMetadata metadata, PathInits inits) {
        this(FacCapa.class, metadata, inits);
    }

    public QFacCapa(Class<? extends FacCapa> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.facCapaId = inits.isInitialized("facCapaId") ? new QFacCapaId(forProperty("facCapaId")) : null;
    }

}

