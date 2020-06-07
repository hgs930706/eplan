package com.lcm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QModChange is a Querydsl query type for ModChange
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QModChange extends EntityPathBase<ModChange> {

    private static final long serialVersionUID = 121548781L;

    public static final QModChange modChange = new QModChange("modChange");

    public final StringPath affectCapaPercent1 = createString("affectCapaPercent1");

    public final StringPath affectCapaPercent2 = createString("affectCapaPercent2");

    public final StringPath affectCapaPercent3 = createString("affectCapaPercent3");

    public final StringPath affectCapaPercent4 = createString("affectCapaPercent4");

    public final StringPath affectCapaQty1 = createString("affectCapaQty1");

    public final StringPath affectCapaQty2 = createString("affectCapaQty2");

    public final StringPath affectCapaQty3 = createString("affectCapaQty3");

    public final StringPath affectCapaQty4 = createString("affectCapaQty4");

    public final StringPath area = createString("area");

    public final StringPath changeDuration = createString("changeDuration");

    public final StringPath changeKey = createString("changeKey");

    public final StringPath changeLevel = createString("changeLevel");

    public final StringPath fab = createString("fab");

    public final StringPath fromBarType = createString("fromBarType");

    public final StringPath fromChangeGroup = createString("fromChangeGroup");

    public final StringPath fromIsBuildPcb = createString("fromIsBuildPcb");

    public final StringPath fromIsDemura = createString("fromIsDemura");

    public final StringPath fromIsOverSixMonth = createString("fromIsOverSixMonth");

    public final StringPath fromModelSite = createString("fromModelSite");

    public final StringPath fromModelType = createString("fromModelType");

    public final StringPath fromPanelSize = createString("fromPanelSize");

    public final StringPath fromPanelSizeGroup = createString("fromPanelSizeGroup");

    public final StringPath fromPartLevel = createString("fromPartLevel");

    public final StringPath fromPartNo = createString("fromPartNo");

    public final StringPath fromPartsGroup = createString("fromPartsGroup");

    public final StringPath fromToReverse = createString("fromToReverse");

    public final StringPath fromTuffyType = createString("fromTuffyType");

    public final StringPath line = createString("line");

    public final DateTimePath<java.time.LocalDateTime> lmTime = createDateTime("lmTime", java.time.LocalDateTime.class);

    public final StringPath lmUser = createString("lmUser");

    public final StringPath priority = createString("priority");

    public final StringPath site = createString("site");

    public final StringPath toBarType = createString("toBarType");

    public final StringPath toChangeGroup = createString("toChangeGroup");

    public final StringPath toIsBuildPcb = createString("toIsBuildPcb");

    public final StringPath toIsDemura = createString("toIsDemura");

    public final StringPath toIsOverSixMonth = createString("toIsOverSixMonth");

    public final StringPath toModelSite = createString("toModelSite");

    public final StringPath toModelType = createString("toModelType");

    public final StringPath toPanelSize = createString("toPanelSize");

    public final StringPath toPanelSizeGroup = createString("toPanelSizeGroup");

    public final StringPath toPartLevel = createString("toPartLevel");

    public final StringPath toPartNo = createString("toPartNo");

    public final StringPath toPartsGroup = createString("toPartsGroup");

    public final StringPath toTuffyType = createString("toTuffyType");

    public QModChange(String variable) {
        super(ModChange.class, forVariable(variable));
    }

    public QModChange(Path<? extends ModChange> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModChange(PathMetadata metadata) {
        super(ModChange.class, metadata);
    }

}

