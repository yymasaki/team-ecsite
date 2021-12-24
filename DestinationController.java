package jp.co.internous.sirius.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.sirius.model.domain.MstDestination;
import jp.co.internous.sirius.model.domain.MstUser;
import jp.co.internous.sirius.model.form.DestinationForm;
import jp.co.internous.sirius.model.mapper.MstDestinationMapper;
import jp.co.internous.sirius.model.mapper.MstUserMapper;
import jp.co.internous.sirius.model.session.LoginSession;

/**
 * 宛先情報に関する処理のコントローラー
 * @author yymasaki
 *
 */
@Controller
@RequestMapping("/sirius/destination")
public class DestinationController {
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private MstDestinationMapper mstDestinationMapper;
	
	@Autowired
	private MstUserMapper mstUserMapper;
	
	private Gson gson = new Gson();
		
	/**
	 * 宛先画面を初期表示する
	 * @param m 画面表示用オブジェクト
	 * @return カート画面
	 */
	@RequestMapping("/")
	public String index(Model m) {
		MstUser user = mstUserMapper.findByUserNameAndPassword(loginSession.getUserName(),loginSession.getPassword());
		m.addAttribute("user", user);
		// page_header.htmlでsessionの変数を表示させているため、loginSessionも画面に送る。
		m.addAttribute("loginSession",loginSession);
		return "destination";
	}
	
	/**
	 * 宛先情報を登録する
	 * @param f 宛先情報のフォーム
	 * @return 宛先情報id
	 */
	@RequestMapping("/register")
	@ResponseBody
	public String register(@RequestBody DestinationForm f) {
		// 宛先を登録
		MstDestination destination = new MstDestination(f);
		int userId = loginSession.getUserId();
		destination.setUserId(userId);
		int count = mstDestinationMapper.insert(destination);
		
		// 登録した宛先のIDを取得
		Integer id = 0;
		if (count > 0) {
			id = destination.getId();
		}
		return id.toString();
	}
	
	/**
	 * 宛先情報を削除する
	 * @param destinationId 宛先情報ID
	 * @return true:削除成功、false:削除失敗
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestBody String destinationId) {

		Map<String, String> map = gson.fromJson(destinationId, Map.class);
		String id = map.get("destinationId");

		int result = mstDestinationMapper.logicalDeleteById(Integer.parseInt(id));

		return result > 0;
	}
}
