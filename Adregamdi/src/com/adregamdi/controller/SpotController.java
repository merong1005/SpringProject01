package com.adregamdi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.adregamdi.api.VisitKoreaAPI;
import com.adregamdi.dto.PageDTO;
import com.adregamdi.dto.SpotDTO;
import com.adregamdi.dto.VisitKoreaDTO;
import com.adregamdi.service.SpotService;


@Controller
@RequestMapping("/spot")
public class SpotController {
	
	private static int totalCount;
	
	@Autowired
	SpotService spotService;
	

	
	@Autowired
	private VisitKoreaAPI spot;

	@GetMapping("/main")
	public String spotMain(@RequestParam(value="currentPage", defaultValue="1")String currentPage,
			@RequestParam(value="sigunguCode", defaultValue="")String sigunguCode, @RequestParam(value="contentTypeId", defaultValue="")String contentTypeId, 
			 Model model ) throws Exception {
		
		totalCount = spot.getTotalCount(contentTypeId, sigunguCode);
		System.out.println("totalCount : "+totalCount);
		model.addAttribute("pageMaker", new PageDTO(currentPage, totalCount, 10));
		model.addAttribute("sigunguCode", sigunguCode);
		model.addAttribute("contentTypeId", contentTypeId);		
		
		return "spot/main";
	}
	
	@ResponseBody
	@GetMapping("/information")
	public List<VisitKoreaDTO> getSpotInfo2(VisitKoreaDTO visitKoreaDTO, Model model) throws Exception {
		
		
		if(visitKoreaDTO.getPageNo()==null) visitKoreaDTO.setPageNo("1");
		if(visitKoreaDTO.getSigunguCode()==null) visitKoreaDTO.setSigunguCode("");
		if(visitKoreaDTO.getContentTypeId()==null) visitKoreaDTO.setContentTypeId("");
		
		/*
		// spotDTO에 초기값 넣을 때 화면
		return spot.getInformation(visitKoreaDTO, totalCount);
		*/
		
		ArrayList<SpotDTO> spotDTO = spotService.getSpotInfo();
		
		return spot.getInformationPlusLike(visitKoreaDTO, spotDTO, totalCount);
	}
	
	@ResponseBody
	@GetMapping("/best")
	public List<VisitKoreaDTO> getBestSpotInfo(VisitKoreaDTO visitKoreaDTO) throws SAXException, IOException, ParserConfigurationException {
		
		if(visitKoreaDTO.getPageNo()==null) visitKoreaDTO.setPageNo("1");
		if(visitKoreaDTO.getSigunguCode()==null) visitKoreaDTO.setSigunguCode("");
		if(visitKoreaDTO.getContentTypeId()==null) visitKoreaDTO.setContentTypeId("");
		
		// 현재 페이지에서 Best 3
		int range_min = ((Integer.parseInt(visitKoreaDTO.getPageNo())-1)*10) + 1 ;
		int range_max = range_min + 9;
		System.out.println("min : "+range_min + " max : "+range_max);
		ArrayList<String> bestContentId = spotService.getBestSpotInfo(range_min, range_max);
		
		System.out.println("bestContentId : "+bestContentId.toString());
		return spot.getBestInformation(visitKoreaDTO, totalCount, bestContentId);
	}
	
	@ResponseBody
	@GetMapping("/details")
	public List<String> getDetailsInfo(String contentId, String contentTypeId) throws Exception {
		
		VisitKoreaDTO visitKoreaDTO = new VisitKoreaDTO();
		
		visitKoreaDTO.setContentId(contentId);
		visitKoreaDTO.setContentTypeId(contentTypeId);
		
		return spot.getEachInformation(visitKoreaDTO);
	}	
	
	@ResponseBody
	@GetMapping("/keyword")
	public List<VisitKoreaDTO> getKeywordInfo(VisitKoreaDTO visitKoreaDTO, String keyword, Model model) throws Exception {
		
		if(visitKoreaDTO.getPageNo()==null) visitKoreaDTO.setPageNo("1");
		if(visitKoreaDTO.getContentTypeId()==null) visitKoreaDTO.setContentTypeId("");
		List<VisitKoreaDTO> resultKeyword = spot.getKeywordInformation(visitKoreaDTO, keyword);
		
		return resultKeyword;
	}
	
	
	@GetMapping("/review") 
	public String review(@RequestParam ("contentId")String contentId, @RequestParam("contentTypeId")String contentTypeId, Model model) throws Exception {
		
		model.addAttribute("contentId", contentId);
		model.addAttribute("contentTypeId", contentTypeId);
		
		VisitKoreaDTO visitKoreaDTO = new VisitKoreaDTO();
		
		visitKoreaDTO.setContentId(contentId);
		visitKoreaDTO.setContentTypeId(contentTypeId);
		
		List<String> information = spot.getEachInformation(visitKoreaDTO);
		
		model.addAttribute("information", information);
		
		//System.out.println(information.get(0));
		
		
		return "spot/review";
	}
	
	@ResponseBody
	@GetMapping("/likeProc")
	public ArrayList<SpotDTO> likeProc(@RequestParam("contentId")String contentId) throws SAXException, IOException, ParserConfigurationException {
		
		/*
		// SpotLikeDTO에 초기 데이터 값 넣기
		ArrayList<String> contentIdList = spot.lgetContentId();
		
		SpotDTO spotDTO = new SpotDTO();
		
		for(int i=0; i<contentIdList.size(); i++) {
			
			spotDTO.setContent_id(contentIdList.get(i));
			spotDTO.setLike_cnt(0);
			spotDTO.setReview_cnt(0);
			
			spotService.inputContentId(spotDTO);
		}
		*/
		
		ArrayList<SpotDTO> spotDTO = spotService.getSpotInfo();
		
		spotService.plusLikeCnt(contentId);
				
		System.out.println(spotDTO.get(0));
		
		return spotDTO;
	}
	 
}
