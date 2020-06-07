package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QModModel is a Querydsl query type for ModModel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QModModel extends EntityPathBase<ModModel> {

    private static final long serialVersionUID = -1233558708L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QModModel modModel = new QModModel("modModel");

    public final StringPath barType = createString("barType");

    public final StringPath changeGroup = createString("changeGroup");

    public final StringPath color = createString("color");

    public final StringPath isBuildPcb = createString("isBuildPcb");

    public final StringPath isDemura = createString("isDemura");

    public final StringPath isOversixmonth = createString("isOversixmonth");

    public final DateTimePath<java.time.LocalDateTime> lastTrackoutTime = createDateTime("lastTrackoutTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath modelExt = createString("modelExt");

    public final QModelId modelId;

    public final StringPath modelNo = createString("modelNo");

    public final StringPath modelSite = createString("modelSite");

    public final StringPath modelType = createString("modelType");

    public final StringPath modelVer = createString("modelVer");

    public final StringPath panelSize = createString("panelSize");

    public final StringPath panelSizeGroup = createString("panelSizeGroup");

    public final StringPath partsGroup = createString("partsGroup");

    public final StringPath priority = createString("priority");

    public final StringPath tuffyType = createString("tuffyType");

    public QModModel(String variable) {
        this(ModModel.class, forVariable(variable), INITS);
    }

    public QModModel(Path<? extends ModModel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QModModel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QModModel(PathMetadata metadata, PathInits inits) {
        this(ModModel.class, metadata, inits);
    }

    public QModModel(Class<? extends ModModel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.modelId = inits.isInitialized("modelId") ? new QModelId(forProperty("modelId")) : null;
    }

}

