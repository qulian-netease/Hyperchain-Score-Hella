package cn.hyperchain.application.controller;

import cn.hyperchain.application.common.response.BaseResult;
import cn.hyperchain.application.contract.invoke.ContractInvoke;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 合约操作
 *
 * @author ZhaoCong
 * @date 2018/10/15
 */
@RestController
public class ContractController {

    @Autowired
    private ContractInvoke contractInvoke;


    /**
     * 银行登录
     *
     * @param address 银行账户地址
     */
    @ApiOperation(value = "银行-登录", notes = "积分系统")
    @RequestMapping(value = "/v1/bankLogin", method = RequestMethod.POST)
    public BaseResult bankLogin(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "账户地址") @RequestParam String address) {
        return contractInvoke.bankLogin(token, invokeAddress, address);
    }

    /**
     * 银行向顾客发送积分
     *
     * @param customerAddress   客户地址
     * @param scoreAmount 积分数量
     * @return
     */
    @ApiOperation(value = "银行-发行积分", notes = "积分系统")
    @RequestMapping(value = "/v1/sendScoreToCustomer", method = RequestMethod.POST)
    public BaseResult sendScoreToCustomer(@ApiParam(value = "accessToken") @RequestParam String token,
                                 @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
                                 @ApiParam(value = "客户地址") @RequestParam String customerAddress,
                                 @ApiParam(value = "积分数量") @RequestParam String scoreAmount) {
        return contractInvoke.sendScoreToCustomer(token, invokeAddress, customerAddress, scoreAmount);
    }


    /**
     * 已发行积分总数
     *
     * @return
     */
    @ApiOperation(value = "银行-已发行积分总数", notes = "积分系统")
    @RequestMapping(value = "/v1/getIssuedScoreAmount", method = RequestMethod.POST)
    public BaseResult getIssuedScoreAmount(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress) {
        return contractInvoke.getIssuedScoreAmount(token, invokeAddress);
    }

    /**
     * 已清算积分总数
     *
     * @return
     */
    @ApiOperation(value = "银行-已清算积分总数", notes = "积分系统")
    @RequestMapping(value = "/v1/getSettledScoreAmount", method = RequestMethod.POST)
    public BaseResult getSettledScoreAmount(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress) {
        return contractInvoke.getSettledScoreAmount(token, invokeAddress);
    }

    /**
     * 客户注册
     *
     * @param customerAddress 客户账户
     * @param customerPassword 密码
     */
    @ApiOperation(value = "客户-注册", notes = "积分系统")
    @RequestMapping(value = "/v2/newcustomer", method = RequestMethod.POST)
    public BaseResult newcustomer(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "客户账户") @RequestParam String customerAddress,
            @ApiParam(value = "密码") @RequestParam String customerPassword) {
        return contractInvoke.newCustomer(token, invokeAddress, customerAddress,customerPassword);
    }

    /**
     * 客户登录
     *
     * @param customerLoginAddr 客户账户
     * @param customerLoginPwd 密码
     */
    @ApiOperation(value = "客户-登录", notes = "积分系统")
    @RequestMapping(value = "/v2/customerLogin", method = RequestMethod.POST)
    public BaseResult customerLogin(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "客户账户") @RequestParam String customerLoginAddr,
            @ApiParam(value = "密码") @RequestParam String customerLoginPwd) {
        return contractInvoke.customerLogin(token, invokeAddress, customerLoginAddr,customerLoginPwd);
    }


    /**
     * 当前余额
     *
     * @param currentAccount 当前账户地址
     * @return
     */
    @ApiOperation(value = "客户-当前余额", notes = "积分系统")
    @RequestMapping(value = "/v2/getScoreWithCustomerAddr", method = RequestMethod.POST)
    public BaseResult getScoreWithCustomerAddr(@ApiParam(value = "accessToken") @RequestParam String token,
                                         @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
                                         @ApiParam(value = "当前账户地址") @RequestParam String currentAccount) {
        return contractInvoke.getScoreWithCustomerAddr(token, invokeAddress, currentAccount);
    }

    /**
     * 查看已购买商品
     *
     * @param currentAccount 当前账户地址
     * @return
     */
    @ApiOperation(value = "客户-已购买商品", notes = "积分系统")
    @RequestMapping(value = "/v2/getGoodsByCustomer", method = RequestMethod.POST)
    public BaseResult getGoodsByCustomer(@ApiParam(value = "accessToken") @RequestParam String token,
                                         @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
                                         @ApiParam(value = "当前账户地址") @RequestParam String currentAccount) {
        return contractInvoke.getGoodsByCustomer(token, invokeAddress, currentAccount);
    }

    /**
     * 转让积分
     *
     * @param sender 发起转账者
     * @param receiver 转账对象
     * @param scoreAmount 积分数量
     * @return
     */
    @ApiOperation(value = "客户-转让积分", notes = "积分系统")
    @RequestMapping(value = "/v2/transferScoreToAnotherFromCustomer", method = RequestMethod.POST)
    public BaseResult transferScoreToAnotherFromCustomer(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "发起转账者") @RequestParam String sender,
            @ApiParam(value = "转账对象") @RequestParam String receiver,
            @ApiParam(value = "积分数量") @RequestParam String scoreAmount) {
        return contractInvoke.transferScoreToAnotherFromCustomer(token, invokeAddress,sender,receiver,scoreAmount);
    }

    /**
     * 购买商品
     *
     * @param goodId 购买商品Id
     * @param currentAccount 当前账户地址
     * @return
     */
    @ApiOperation(value = "客户-购买商品", notes = "积分系统")
    @RequestMapping(value = "/v2/buyGood", method = RequestMethod.POST)
    public BaseResult buyGood(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "购买账户") @RequestParam String currentAccount,
            @ApiParam(value = "商品Id") @RequestParam String goodId) {
        return contractInvoke.buyGood(token, invokeAddress,currentAccount, goodId);
    }



    /**
     * 商户注册
     *
     * @param merchantAddress 商户账户
     * @param merchantPassword 密码
     */
    @ApiOperation(value = "商户-注册", notes = "积分系统")
    @RequestMapping(value = "/v3/newMerchant", method = RequestMethod.POST)
    public BaseResult newMerchant(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "商户账户") @RequestParam String merchantAddress,
            @ApiParam(value = "密码") @RequestParam String merchantPassword) {
        return contractInvoke.newMerchant(token, invokeAddress, merchantAddress,merchantPassword);
    }

    /**
     * 商户登录
     *
     * @param merchantLoginAddr 商户账户
     * @param merchantLoginPwd 密码
     */
    @ApiOperation(value = "商户-登录", notes = "积分系统")
    @RequestMapping(value = "/v3/merchantLogin", method = RequestMethod.POST)
    public BaseResult merchantLogin(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "商户账户") @RequestParam String merchantLoginAddr,
            @ApiParam(value = "密码") @RequestParam String merchantLoginPwd) {
        return contractInvoke.merchantLogin(token, invokeAddress, merchantLoginAddr,merchantLoginPwd);
    }


    /**
     * 当前余额
     *
     * @param currentAccount 当前账户地址
     * @return
     */
    @ApiOperation(value = "商户-当前余额", notes = "积分系统")
    @RequestMapping(value = "/v3/getScoreWithMerchantAddr", method = RequestMethod.POST)
    public BaseResult getScoreWithMerchantAddr(@ApiParam(value = "accessToken") @RequestParam String token,
                                               @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
                                               @ApiParam(value = "当前账户地址") @RequestParam String currentAccount) {
        return contractInvoke.getScoreWithMerchantAddr(token, invokeAddress, currentAccount);
    }

    /**
     * 转让积分
     *
     * @param sender 发起转账者
     * @param receiver 转账对象
     * @param scoreAmount 积分数量
     * @return
     */
    @ApiOperation(value = "商户-转让积分", notes = "积分系统")
    @RequestMapping(value = "/v3/transferScoreToAnotherFromMerchant", method = RequestMethod.POST)
    public BaseResult transferScoreToAnotherFromMerchant(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "发起转账者") @RequestParam String sender,
            @ApiParam(value = "转账对象") @RequestParam String receiver,
            @ApiParam(value = "积分数量") @RequestParam String scoreAmount) {
        return contractInvoke.transferScoreToAnotherFromMerchant(token,invokeAddress,sender,receiver,scoreAmount);
    }

    /**
     * 添加商品
     *
     * @param goodId 商品Id
     * @param goodPrice 商品价格
     * @param currentAccount 当前账户地址
     * @return
     */
    @ApiOperation(value = "商户-添加商品", notes = "积分系统")
    @RequestMapping(value = "/v3/addGood", method = RequestMethod.POST)
    public BaseResult addGood(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "当前账户地址") @RequestParam String currentAccount,
            @ApiParam(value = "商品Id") @RequestParam String goodId,
            @ApiParam(value = "商品价格") @RequestParam String goodPrice) {
        return contractInvoke.addGood(token, invokeAddress,currentAccount, goodId,goodPrice);
    }

    /**
     * 查看已添加商品
     *
     * @param currentAccount 当前账户地址
     * @return
     */
    @ApiOperation(value = "商户-查看已添加商品", notes = "积分系统")
    @RequestMapping(value = "/v3/getGoodsByMerchant", method = RequestMethod.POST)
    public BaseResult getGoodsByMerchant(@ApiParam(value = "accessToken") @RequestParam String token,
                                               @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
                                               @ApiParam(value = "当前账户地址") @RequestParam String currentAccount) {
        return contractInvoke.getGoodsByMerchant(token, invokeAddress, currentAccount);
    }

    /**
     * 清算积分
     *
     * @param settleAmount 清算积分数额
     * @param currentAccount 当前账户地址
     * @return
     */
    @ApiOperation(value = "商户-清算积分", notes = "积分系统")
    @RequestMapping(value = "/v3/settleScoreWithBank", method = RequestMethod.POST)
    public BaseResult settleScoreWithBank(@ApiParam(value = "accessToken") @RequestParam String token,
                                         @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
                                          @ApiParam(value = "当前账户地址") @RequestParam String currentAccount,
                                         @ApiParam(value = "清算积分数额") @RequestParam String settleAmount) {
        return contractInvoke.settleScoreWithBank(token, invokeAddress, currentAccount, settleAmount);
    }


}
