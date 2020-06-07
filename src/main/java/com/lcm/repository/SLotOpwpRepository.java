package com.lcm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lcm.domain.SLotOpwp;
import com.lcm.domain.SLotOpwpId;

public interface SLotOpwpRepository extends JpaRepository<SLotOpwp, SLotOpwpId> {
	
	@Query("select s from SLotOpwp s where s.sLotOpwpId.site=?1 and s.sLotOpwpId.fab in ?2 and s.sLotOpwpId.area in ?3"
			+ " and s.sLotOpwpId.shiftDate = ?4"
			+ " and s.sLotOpwpId.shift='N'"
			+ " and s.sLotOpwpId.lotType='N'"
			+ " order by s.sLotOpwpId.shiftDate, s.sLotOpwpId.partNo")
	List<SLotOpwp> findBySiteAndFabInAndAreaInAndShiftDateOrderbyShiftDatePartNo(String site, List<String> fabList, List<String> areaList,
			LocalDate planStartDate);
}
