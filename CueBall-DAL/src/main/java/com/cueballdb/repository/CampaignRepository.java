//package com.cueballdb.repository;
//
//import com.cueballdb.model.Campaign;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
////@Repository
//public interface CampaignRepository extends JpaRepository<Campaign, Integer>, CampaignCustomRepository {
//
//    Campaign findByRomanUrl(String romanUrl);
//
//    @Query(value = "SELECT count(c.id) FROM `campaign` c " , nativeQuery = true)
//    Integer findCampaignCount();
//
//    @Query(value = "SELECT count(c.id) FROM `campaign` c where FIND_IN_SET(operating_unit_id, :operatingUnitIds) > 0 " , nativeQuery = true)
//    Integer findCampaignCountByOperatingUnits(String operatingUnitIds);
//
//    @Query(value = "SELECT * FROM campaign c where c.id <> ?1 and c.roman_url =?2 and c.operating_unit_id =?3", nativeQuery = true)
//    List<Campaign> findByRomanUrlAndOperatingUnit_Id(int currentId, String romanUrl, int operatingUnitId);
//
//    @Query(value = "SELECT * FROM campaign c where c.id <> ?1 and c.root =?2 and c.operating_unit_id =?3", nativeQuery = true)
//    List<Campaign> findByRootAndOperatingUnit_Id(int currentId, boolean root, int operatingUnitId);
//
//    @Query(value = "SELECT * FROM campaign c where FIND_IN_SET(operating_unit_id, :operatingUnitIds) > 0 ", nativeQuery = true)
//    List<Campaign> findByOperatingUnitIds(String operatingUnitIds);
//
//
////  @Query(value = "SELECT * FROM campaign c where c.legal_entity_id=?1 and c.operating_unit_id=?2 and c.enable=1 and c.show_home=1 and NOW() between c.start_date and c.end_date order by c.id desc limit 10", nativeQuery = true)
//    @Query(value = "SELECT * FROM campaign c WHERE c.legal_entity_id = ?1 AND c.operating_unit_id = ?2 AND c.enable = 1 AND c.show_home = 1 AND (CASE WHEN c.life_time = 1 THEN DATE(c.start_date) <= DATE(NOW()) ELSE DATE(NOW()) BETWEEN DATE(c.start_date) AND DATE(c.end_date) END) ORDER BY c.id DESC LIMIT 10", nativeQuery = true)
//    List<Campaign> findCampaigns(Integer legal_entity_id, Integer operating_unit_id);
//
//
//    @Query(value = "SELECT * FROM campaign c where c.legal_entity_id=?1 and c.operating_unit_id=?2 and c.root =?3 and c.roman_url =?4 and c.enable=1", nativeQuery = true)
//    Campaign findCampaign(Integer legal_entity_id, Integer operating_unit_id ,boolean root,String romanUrl );
//
//
//}
