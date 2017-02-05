package com.angkorteam.ecommerce.controller;

import com.angkorteam.ecommerce.mobile.delivery.*;
import com.angkorteam.ecommerce.model.EcommerceBranch;
import com.angkorteam.framework.jdbc.JoinType;
import com.angkorteam.framework.jdbc.SelectQuery;
import com.angkorteam.framework.spring.JdbcTemplate;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by socheatkhauv on 27/1/17.
 */
@Controller
public class BranchesServiceGet {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchesServiceGet.class);

    @Autowired
    @Qualifier("gson")
    private Gson gson;

    @RequestMapping(path = "/{shop}/branches", method = RequestMethod.GET)
    public ResponseEntity<?> service(HttpServletRequest request) {
        JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);

        SelectQuery selectQuery = null;

        List<EcommerceBranch> branchRecords = jdbcTemplate.queryForList("select * from ecommerce_branch", EcommerceBranch.class);

        List<Branch> branches = Lists.newArrayList();

        for (EcommerceBranch branchRecord : branchRecords) {
            Branch branch = new Branch();
            branch.setId(branchRecord.getEcommerceBranchId());
            branch.setName(branchRecord.getName());
            branch.setAddress(branchRecord.getAddress());
            Coordinates coordinatesVO = new Coordinates();
            coordinatesVO.setLatitude(branchRecord.getLatitude());
            coordinatesVO.setLongitude(branchRecord.getLongitude());
            branch.setCoordinates(coordinatesVO);
            branch.setNote(branchRecord.getNote());
            branches.add(branch);

            selectQuery = new SelectQuery("ecommerce_branch_opening");
            selectQuery.addField("day");
            selectQuery.addField("opening");
            selectQuery.addWhere("ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getEcommerceBranchId());
            List<OpeningHours> openingRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), OpeningHours.class);
            if (openingRecords != null && !openingRecords.isEmpty()) {
                branch.setOpeningHoursList(openingRecords);
            }

            selectQuery = new SelectQuery("ecommerce_branch_transport");
            selectQuery.addJoin(JoinType.LeftJoin, "platform_file", "ecommerce_branch_transport.icon_platform_file_id = platform_file.platform_file_id");
            selectQuery.addField("ecommerce_branch_transport.text AS text");
            selectQuery.addField("CONCAT('http://mbaas.angkorteam.com/api/resource', platform_file.path, '/', platform_file.name) AS icon");
            selectQuery.addWhere("ecommerce_branch_transport.ecommerce_branch_id = :ecommerce_branch_id", branchRecord.getEcommerceBranchId());
            List<Transport> transportRecords = named.queryForList(selectQuery.toSQL(), selectQuery.getParam(), Transport.class);
            if (transportRecords != null && !transportRecords.isEmpty()) {
                branch.setTransports(transportRecords);
            }
        }

        BranchesRequest data = new BranchesRequest();
        data.setRecords(branches);

        return ResponseEntity.ok(data);
    }

}