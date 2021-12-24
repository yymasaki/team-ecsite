package jp.co.internous.sirius.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.sirius.model.domain.MstDestination;

/**
 * mst_destinationにアクセスするDAO
 * @author yymasaki
 *
 */
@Mapper
public interface MstDestinationMapper {
	
	/**
	 * 宛先情報IDを条件に論理削除する
	 * @param id 宛先情報ID
	 * @return 削除件数
	 */
	@Update("UPDATE mst_destination SET status = 0, updated_at = now() WHERE id = #{id}")
	int logicalDeleteById(@Param("id") int id);
	
	/**
	 * データを登録する。
	 * @param destination 宛先情報
	 * @return 登録件数
	 */
	@Insert("INSERT INTO mst_destination(user_id, family_name, first_name, tel_number, address)" +
				"VALUES(#{userId}, #{familyName}, #{firstName}, #{telNumber}, #{address})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	int insert(MstDestination mstDestination);
	
	/**
	 * ユーザーIDを条件に宛先情報を取得する
	 * @param userId ユーザーID
	 * @return 宛先情報リスト
	 */
	@Select("SELECT * FROM mst_destination WHERE user_id = #{userId} AND status = 1 ORDER BY id ASC")
	List<MstDestination> findByUserId(@Param("userId") long userId);
	

}
