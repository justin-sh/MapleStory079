package com.justin.game.ms079.dao;

import com.justin.game.ms079.dto.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountDAO {

    @Select("select 2ndpassword password2nd, accounts.* from accounts order by id")
    List<Account> getAccounts();

    @Select("select 2ndpassword password2nd, accounts.* from accounts where id = #{id}#")
    Account getAccountById(int id);

}
