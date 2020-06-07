package com.lcm.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "C_MOD_CHANGE")
public class ModChange implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "site")
    private String site;

    @Column(name = "fab")
    private String fab;

    @Column(name = "area")
    private String area;

    @Column(name = "line")
    private String line;

    @Column(name = "from_model_site")
    private String fromModelSite;

    @Column(name = "from_model_type")
    private String fromModelType;

    @Column(name = "from_panel_size")
    private String fromPanelSize;

    @Column(name = "from_part_level")
    private String fromPartLevel;

    @Column(name = "from_bar_type")
    private String fromBarType;

    @Column(name = "from_panel_size_group")
    private String fromPanelSizeGroup;

    @Column(name = "from_parts_group")
    private String fromPartsGroup;

    @Column(name = "from_is_build_pcb")
    private String fromIsBuildPcb;

    @Column(name = "from_is_demura")
    private String fromIsDemura;

    @Column(name = "from_tuffy_type")
    private String fromTuffyType;

    @Column(name = "from_is_over_six_month")
    private String fromIsOverSixMonth;

    @Column(name = "to_model_site")
    private String toModelSite;

    @Column(name = "to_model_type")
    private String toModelType;

    @Column(name = "to_panel_size")
    private String toPanelSize;

    @Column(name = "to_part_level")
    private String toPartLevel;

    @Column(name = "to_bar_type")
    private String toBarType;

    @Column(name = "to_panel_size_group")
    private String toPanelSizeGroup;

    @Column(name = "to_parts_group")
    private String toPartsGroup;

    @Column(name = "to_is_build_pcb")
    private String toIsBuildPcb;

    @Column(name = "to_is_demura")
    private String toIsDemura;

    @Column(name = "to_tuffy_type")
    private String toTuffyType;

    @Column(name = "to_is_over_six_month")
    private String toIsOverSixMonth;

    @Id
    @Column(name = "change_key")
    private String changeKey;

    @Column(name = "change_level")
    private String changeLevel;

    @Column(name = "change_duration")
    private String changeDuration;

    @Column(name = "affect_capa_percent_1")
    private String affectCapaPercent1;

    @Column(name = "affect_capa_qty_1")
    private String affectCapaQty1;

    @Column(name = "affect_capa_percent_2")
    private String affectCapaPercent2;

    @Column(name = "affect_capa_qty_2")
    private String affectCapaQty2;

    @Column(name = "affect_capa_percent_3")
    private String affectCapaPercent3;

    @Column(name = "affect_capa_qty_3")
    private String affectCapaQty3;

    @Column(name = "affect_capa_percent_4")
    private String affectCapaPercent4;

    @Column(name = "affect_capa_qty_4")
    private String affectCapaQty4;

    @Column(name = "lm_user")
    private String lmUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "lm_time")
    private LocalDateTime lmTime;

    @Column(name = "priority")
    private String priority;

    @Column(name = "from_part_no")
    private String fromPartNo;

    @Column(name = "to_part_no")
    private String toPartNo;

    @Column(name="FROM_TO_REVERSE")
    private String fromToReverse;

    @Column(name="FROM_CHANGE_GROUP")
    private String fromChangeGroup;

    @Column(name="TO_CHANGE_GROUP")
    private String toChangeGroup;
    
    //10月中的版本再上,先拿掉 JoshLai@20190927+
//    @Column(name="line_mode")
//    private String lineMode;

    public String getFromToReverse() {
        return fromToReverse;
    }

    public void setFromToReverse(String fromToReverse) {
        this.fromToReverse = fromToReverse;
    }

    public String getFromChangeGroup() {
        return fromChangeGroup;
    }

    public void setFromChangeGroup(String fromChangeGroup) {
        this.fromChangeGroup = fromChangeGroup;
    }

    public String getToChangeGroup() {
        return toChangeGroup;
    }

    public void setToChangeGroup(String toChangeGroup) {
        this.toChangeGroup = toChangeGroup;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFab() {
        return fab;
    }

    public void setFab(String fab) {
        this.fab = fab;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getFromModelSite() {
        return fromModelSite;
    }

    public void setFromModelSite(String fromModelSite) {
        this.fromModelSite = fromModelSite;
    }

    public String getFromModelType() {
        return fromModelType;
    }

    public void setFromModelType(String fromModelType) {
        this.fromModelType = fromModelType;
    }

    public String getFromPanelSize() {
        return fromPanelSize;
    }

    public void setFromPanelSize(String fromPanelSize) {
        this.fromPanelSize = fromPanelSize;
    }

    public String getFromPartLevel() {
        return fromPartLevel;
    }

    public void setFromPartLevel(String fromPartLevel) {
        this.fromPartLevel = fromPartLevel;
    }

    public String getFromPanelSizeGroup() {
        return fromPanelSizeGroup;
    }

    public void setFromPanelSizeGroup(String fromPanelSizeGroup) {
        this.fromPanelSizeGroup = fromPanelSizeGroup;
    }

    public String getFromPartsGroup() {
        return fromPartsGroup;
    }

    public void setFromPartsGroup(String fromPartsGroup) {
        this.fromPartsGroup = fromPartsGroup;
    }

    public String getFromIsBuildPcb() {
        return fromIsBuildPcb;
    }

    public void setFromIsBuildPcb(String fromIsBuildPcb) {
        this.fromIsBuildPcb = fromIsBuildPcb;
    }

    public String getFromIsDemura() {
        return fromIsDemura;
    }

    public void setFromIsDemura(String fromIsDemura) {
        this.fromIsDemura = fromIsDemura;
    }

    public String getFromTuffyType() {
        return fromTuffyType;
    }

    public void setFromTuffyType(String fromTuffyType) {
        this.fromTuffyType = fromTuffyType;
    }

    public String getFromIsOverSixMonth() {
        return fromIsOverSixMonth;
    }

    public void setFromIsOverSixMonth(String fromIsOverSixMonth) {
        this.fromIsOverSixMonth = fromIsOverSixMonth;
    }

    public String getToModelSite() {
        return toModelSite;
    }

    public void setToModelSite(String toModelSite) {
        this.toModelSite = toModelSite;
    }

    public String getToModelType() {
        return toModelType;
    }

    public void setToModelType(String toModelType) {
        this.toModelType = toModelType;
    }

    public String getToPanelSize() {
        return toPanelSize;
    }

    public void setToPanelSize(String toPanelSize) {
        this.toPanelSize = toPanelSize;
    }

    public String getToPartLevel() {
        return toPartLevel;
    }

    public void setToPartLevel(String toPartLevel) {
        this.toPartLevel = toPartLevel;
    }

    public String getToBarType() {
        return toBarType;
    }

    public void setToBarType(String toBarType) {
        this.toBarType = toBarType;
    }

    public String getToPanelSizeGroup() {
        return toPanelSizeGroup;
    }

    public void setToPanelSizeGroup(String toPanelSizeGroup) {
        this.toPanelSizeGroup = toPanelSizeGroup;
    }

    public String getToPartsGroup() {
        return toPartsGroup;
    }

    public void setToPartsGroup(String toPartsGroup) {
        this.toPartsGroup = toPartsGroup;
    }

    public String getToIsBuildPcb() {
        return toIsBuildPcb;
    }

    public void setToIsBuildPcb(String toIsBuildPcb) {
        this.toIsBuildPcb = toIsBuildPcb;
    }

    public String getToIsDemura() {
        return toIsDemura;
    }

    public void setToIsDemura(String toIsDemura) {
        this.toIsDemura = toIsDemura;
    }

    public String getToTuffyType() {
        return toTuffyType;
    }

    public void setToTuffyType(String toTuffyType) {
        this.toTuffyType = toTuffyType;
    }

    public String getToIsOverSixMonth() {
        return toIsOverSixMonth;
    }

    public void setToIsOverSixMonth(String toIsOverSixMonth) {
        this.toIsOverSixMonth = toIsOverSixMonth;
    }

    public String getChangeKey() {
        return changeKey;
    }

    public void setChangeKey(String changeKey) {
        this.changeKey = changeKey;
    }

    public String getChangeLevel() {
        return changeLevel;
    }

    public void setChangeLevel(String changeLevel) {
        this.changeLevel = changeLevel;
    }

    public String getChangeDuration() {
        return changeDuration;
    }

    public void setChangeDuration(String changeDuration) {
        this.changeDuration = changeDuration;
    }

    public String getAffectCapaPercent1() {
        return affectCapaPercent1;
    }

    public void setAffectCapaPercent1(String affectCapaPercent1) {
        this.affectCapaPercent1 = affectCapaPercent1;
    }

    public String getAffectCapaQty1() {
        return affectCapaQty1;
    }

    public void setAffectCapaQty1(String affectCapaQty1) {
        this.affectCapaQty1 = affectCapaQty1;
    }

    public String getAffectCapaPercent2() {
        return affectCapaPercent2;
    }

    public void setAffectCapaPercent2(String affectCapaPercent2) {
        this.affectCapaPercent2 = affectCapaPercent2;
    }

    public String getAffectCapaQty2() {
        return affectCapaQty2;
    }

    public void setAffectCapaQty2(String affectCapaQty2) {
        this.affectCapaQty2 = affectCapaQty2;
    }

    public String getAffectCapaPercent3() {
        return affectCapaPercent3;
    }

    public void setAffectCapaPercent3(String affectCapaPercent3) {
        this.affectCapaPercent3 = affectCapaPercent3;
    }

    public String getAffectCapaQty3() {
        return affectCapaQty3;
    }

    public void setAffectCapaQty3(String affectCapaQty3) {
        this.affectCapaQty3 = affectCapaQty3;
    }

    public String getAffectCapaPercent4() {
        return affectCapaPercent4;
    }

    public void setAffectCapaPercent4(String affectCapaPercent4) {
        this.affectCapaPercent4 = affectCapaPercent4;
    }

    public String getAffectCapaQty4() {
        return affectCapaQty4;
    }

    public void setAffectCapaQty4(String affectCapaQty4) {
        this.affectCapaQty4 = affectCapaQty4;
    }

    public String getLmUser() {
        return lmUser;
    }

    public void setLmUser(String lmUser) {
        this.lmUser = lmUser;
    }

    public LocalDateTime getLmTime() {
        return lmTime;
    }

    public void setLmTime(LocalDateTime lmTime) {
        this.lmTime = lmTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getFromBarType() {
        return fromBarType;
    }

    public void setFromBarType(String fromBarType) {
        this.fromBarType = fromBarType;
    }

    public String getFromPartNo() {
        return fromPartNo;
    }

    public void setFromPartNo(String fromPartNo) {
        this.fromPartNo = fromPartNo;
    }

    public String getToPartNo() {
        return toPartNo;
    }

    public void setToPartNo(String toPartNo) {
        this.toPartNo = toPartNo;
    }
    
//    public String getLineMode() {
//		return lineMode;
//	}

//	public void setLineMode(String lineMode) {
//		this.lineMode = lineMode;
//	}

	public boolean isMatch(String site, String fab, String line, String lineMode, List<String> fromList, List<String> toList) {
		boolean isMatch = true;
		List<String> thisFromList = new ArrayList<>();
		List<String> thisToList = new ArrayList<>();
		thisFromList.add(this.fromModelSite);
		thisFromList.add(this.fromModelType);
		thisFromList.add(this.fromPanelSize);
		thisFromList.add(this.fromPartNo);
		thisFromList.add(this.fromPartLevel);
		thisFromList.add(this.fromBarType);
		thisFromList.add(this.fromPanelSizeGroup);
		thisFromList.add(this.fromPartsGroup);
		thisFromList.add(this.fromIsBuildPcb);
		thisFromList.add(this.fromIsDemura);
		thisFromList.add(this.fromTuffyType);
		thisFromList.add(this.fromIsOverSixMonth);
		thisFromList.add(this.fromChangeGroup);//add by avonchung 20190429
		thisToList.add(this.toModelSite);
		thisToList.add(this.toModelType);
		thisToList.add(this.toPanelSize);
		thisToList.add(this.toPartNo);
		thisToList.add(this.toPartLevel);
		thisToList.add(this.toBarType);
		thisToList.add(this.toPanelSizeGroup);
		thisToList.add(this.toPartsGroup);
		thisToList.add(this.toIsBuildPcb);
		thisToList.add(this.toIsDemura);
		thisToList.add(this.toTuffyType);
		thisToList.add(this.toIsOverSixMonth);
		thisToList.add(this.toChangeGroup);//add by avonchung 20190429
		
		if(!this.site.equals(site)) {
			isMatch = false;
		}else if(!this.fab.equals("%") && !this.fab.equals(fab)) {
			isMatch = false;
		}else if(!this.line.equals("%") && !this.line.equals(line)) {
			isMatch = false;
		}
//		else if(!this.lineMode.equals("%") && !this.lineMode.equals(lineMode)) { //MA新增lineMode JoshLai@20190912+
//			isMatch = false;
//		}
			
		if(isMatch) {
			for(int i = 0; i<thisFromList.size(); i++) {
				String str = thisFromList.get(i);
				if(str.equals("%"))
					continue;
				if(str.equals("[DIFF]")) {
					if(fromList.get(i).equals(toList.get(i))) {
						isMatch = false;
						break;
					}
				}else if(str.equals("[SAME]")) {
					if(!fromList.get(i).equals(toList.get(i))) {
						isMatch = false;
						break;
					}
				}else {
					if(!str.equals(fromList.get(i))){
						isMatch = false;
						break;
					}
				}
			}
			for(int i = 0; i<thisToList.size(); i++) {
				String str = thisToList.get(i);
				if(str.equals("%") || str.equals("[DIFF]") || str.equals("[SAME]"))
					continue;
				if(!str.equals(toList.get(i))){
					isMatch = false;
					break;
				}
			}
		}
		return isMatch;
	}

	public boolean isMatch(String site, String fab, String line, String changeLvl, List<String> toList) {
		boolean isMatch = true;
		List<String> thisToList = new ArrayList<>();
		thisToList.add(this.toModelSite);
		thisToList.add(this.toModelType);
		thisToList.add(this.toPanelSize);
		thisToList.add(this.toPartNo);
		thisToList.add(this.toPartLevel);
		thisToList.add(this.toBarType);
		thisToList.add(this.toPanelSizeGroup);
		thisToList.add(this.toPartsGroup);
		thisToList.add(this.toIsBuildPcb);
		thisToList.add(this.toIsDemura);
		thisToList.add(this.toTuffyType);
		thisToList.add(this.toIsOverSixMonth);
		thisToList.add(this.toChangeGroup);//add by avonchung 20190429
		
		if(!this.site.equals(site)) {
			isMatch = false;
		}else if(!this.fab.equals(fab)) {
			isMatch = false;
		}else if(!this.line.equals("%") && !this.line.equals(line)) {
			isMatch = false;
		}else if(this.changeLevel != null && !this.changeLevel.equals(changeLvl)) {//update by avonchung 20190424 this.changeLevel != null
			isMatch = false;
		}
		
		if(isMatch) {
			for(int i = 0; i<thisToList.size(); i++) {
				String str = thisToList.get(i);
				if(str.equals("%") || str.equals("[DIFF]") || str.equals("[SAME]"))
					continue;
				if(!str.equals(toList.get(i))){
					isMatch = false;
					break;
				}
			}
		}
		
		return isMatch;
	}

	@Override
	public String toString() {
		return "ModChange [site=" + site + ", fab=" + fab + ", area=" + area + ", line=" + line + ", fromModelSite="
				+ fromModelSite + ", fromModelType=" + fromModelType + ", fromPanelSize=" + fromPanelSize
				+ ", fromPartLevel=" + fromPartLevel + ", fromBarType=" + fromBarType + ", fromPanelSizeGroup="
				+ fromPanelSizeGroup + ", fromPartsGroup=" + fromPartsGroup + ", fromIsBuildPcb=" + fromIsBuildPcb
				+ ", fromIsDemura=" + fromIsDemura + ", fromTuffyType=" + fromTuffyType + ", fromIsOverSixMonth="
				+ fromIsOverSixMonth + ", toModelSite=" + toModelSite + ", toModelType=" + toModelType
				+ ", toPanelSize=" + toPanelSize + ", toPartLevel=" + toPartLevel + ", toBarType=" + toBarType
				+ ", toPanelSizeGroup=" + toPanelSizeGroup + ", toPartsGroup=" + toPartsGroup + ", toIsBuildPcb="
				+ toIsBuildPcb + ", toIsDemura=" + toIsDemura + ", toTuffyType=" + toTuffyType + ", toIsOverSixMonth="
				+ toIsOverSixMonth + ", changeKey=" + changeKey + ", changeLevel=" + changeLevel + ", changeDuration="
				+ changeDuration + ", affectCapaPercent1=" + affectCapaPercent1 + ", affectCapaQty1=" + affectCapaQty1
				+ ", affectCapaPercent2=" + affectCapaPercent2 + ", affectCapaQty2=" + affectCapaQty2
				+ ", affectCapaPercent3=" + affectCapaPercent3 + ", affectCapaQty3=" + affectCapaQty3
				+ ", affectCapaPercent4=" + affectCapaPercent4 + ", affectCapaQty4=" + affectCapaQty4 + ", lmUser="
				+ lmUser + ", lmTime=" + lmTime + ", priority=" + priority + ", fromPartNo=" + fromPartNo
				+ ", toPartNo=" + toPartNo + ", fromToReverse=" + fromToReverse + ", fromChangeGroup=" + fromChangeGroup
				+ ", toChangeGroup=" + toChangeGroup + "]";
	}
}
