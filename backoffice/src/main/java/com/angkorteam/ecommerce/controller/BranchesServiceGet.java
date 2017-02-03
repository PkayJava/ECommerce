//package com.angkorteam.ecommerce.controller;
//
//import com.angkorteam.framework.spring.JdbcTemplate;
//import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
//import com.angkorteam.platform.Spring;
//import com.google.gson.Gson;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * Created by socheatkhauv on 27/1/17.
// */
//@Controller
//public class BranchesServiceGet {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(BranchesServiceGet.class);
//
//    @Autowired
//    @Qualifier("gson")
//    private Gson gson;
//
//    @RequestMapping(path = "/{shop}/branches", method = RequestMethod.GET)
//    public ResponseEntity<?> service(HttpServletRequest request) {
//        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
//        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
////        List<ECommerceBranch> branchRecords = this.jdbcTemplate.queryForList("select * from ecommerce_branch", ECommerceBranch.class);
////
////        List<BranchVO> branches = Lists.newArrayList();
////
////        for (ECommerceBranch branchRecord : branchRecords) {
////            BranchVO branch = new BranchVO();
////            branch.setId(branchRecord.getECommerceBranchId());
////            branch.setName(branchRecord.getName());
////            branch.setAddress(branchRecord.getAddress());
////            CoordinatesVO coordinatesVO = new CoordinatesVO();
////            coordinatesVO.setLatitude(branchRecord.getLatitude());
////            coordinatesVO.setLongitude(branchRecord.getLongitue());
////            branch.setCoordinates(coordinatesVO);
////            branch.setNote(branchRecord.getNote());
////            branches.add(branch);
////
////            SelectQuery selectQuery = new SelectQuery("ecommerce_branch_opening");
////            selectQuery.addField("day");
////            selectQuery.addField("openning");
////            selectQuery.addWhere("ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getECommerceBranchId());
////            List<OpeningHoursVO> openingRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), OpeningHoursVO.class);
////            if (openingRecords != null && !openingRecords.isEmpty()) {
////                branch.setOpeningHoursList(openingRecords);
////            }
////
////            selectQuery = new SelectQuery("ecommerce_branch_transport");
////            selectQuery.addJoin(JoinType.LeftJoin, "file", "ecommerce_branch_transport.icon_file_id = file.file_id");
////            selectQuery.addField("ecommerce_branch_transport.text AS text");
////            selectQuery.addField("CONCAT('http://mbaas.angkorteam.com/api/resource', file.path, '/', file.name) AS icon");
////            selectQuery.addWhere("ecommerce_branch_transport.ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getECommerceBranchId());
////            List<TransportVO> transportRecords = this.named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), TransportVO.class);
////            if (transportRecords != null && !transportRecords.isEmpty()) {
////                branch.setTransports(transportRecords);
////            }
////        }
////
////        Map<String, Object> response = Maps.newHashMap();
////        response.put("records", branches);
////
////        return ResponseEntity.ok(response);
//        return null;
//    }
//
//}