package cn.hyperchain.application.contract.invoke;

import cn.hyperchain.application.common.constant.Code;
import cn.hyperchain.application.common.response.BaseResult;
import cn.hyperchain.application.common.response.BaseResultFactory;
import cn.hyperchain.application.common.utils.ContractUtils;
import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionDecode;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * @author ZhaoCong
 * @date 2018/10/15
 */
@Component
public class ContractInvoke {

    private static QsnarkAPI api = new QsnarkAPI();

    private final static String FLAG_OK = "ok";
    /**
     * 银行登录
     */
    public BaseResult bankLogin(String token,String invokeAddress,String address){
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[0];
        //arrFunParamReal[0] = new FuncParamReal("address", address);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            /**
             * 同步调用合约，返回结果放入getTxReciptReturn中
             */
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getOwner",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;
        /**
         * 对getTxReciptReturn进行解析
         */

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                String data=FunctionDecode.resultDecode("getOwner", ContractUtils.getAbi(), getTxReciptReturn.getRet());

                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        data);
                /**
                 * 判断密码是否正确
                 */
                JSONArray jsonArray = JSON.parseArray(data);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String addressTemp=jsonObject.getString("mayvalue");
                if(addressTemp.equals(address)){
                    //登陆成功
                    baseResult.setMessage("登陆成功");
                }else{
                    baseResult.setCode(Code.INVOKE_FAIL.getCode());
                    baseResult.setMessage("登录失败");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getOwner", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;

    }

    /**
     * 银行向顾客发送积分
     */
    public BaseResult sendScoreToCustomer(String token, String invokeAddress, String customerAddress, String scoreAmount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[2];
        arrFunParamReal[0] = new FuncParamReal("address", customerAddress);
        arrFunParamReal[1] = new FuncParamReal("uint256", scoreAmount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "sendScoreToCustomer",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("sendScoreToCustomer", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("sendScoreToCustomer", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 银行获取已经发行的积分
     */
    public BaseResult getIssuedScoreAmount(String token, String invokeAddress) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[0];
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getIssuedScoreAmount",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("getIssuedScoreAmount", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getIssuedScoreAmount", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 银行获得已经清算的积分
     */
    public BaseResult getSettledScoreAmount(String token, String invokeAddress) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[0];
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getSettledScoreAmount",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("getSettledScoreAmount", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getSettledScoreAmount", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 客户注册
     */
    public BaseResult newCustomer(String token, String invokeAddress, String customerAddress, String customerPassword) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[2];
        arrFunParamReal[0] = new FuncParamReal("address", customerAddress);
        arrFunParamReal[1] = new FuncParamReal("bytes32", customerPassword);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "newCustomer",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("newCustomer", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("newCustomer", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 客户登录
     */
    public BaseResult customerLogin(String token, String invokeAddress, String customerLoginAddr, String customerLoginPwd) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("address", customerLoginAddr);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getCustomerPassword",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {

                String data=FunctionDecode.resultDecode("getCustomerPassword", ContractUtils.getAbi(), getTxReciptReturn.getRet());

                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        data);
                /**
                 * 判断密码是否正确
                 */
                JSONArray jsonArray = JSON.parseArray(data);
                JSONObject jsonObject = jsonArray.getJSONObject(1);
                String password=jsonObject.getString("mayvalue");
                if(password.equals(customerLoginPwd)){
                    //登陆成功
                    baseResult.setMessage("登陆成功");
                }else{
                    baseResult.setCode(Code.INVOKE_FAIL.getCode());
                    baseResult.setMessage("登录失败");
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getCustomerPassword", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        return baseResult;
    }

    /**
     * 客户查询当前余额
     */
    public BaseResult getScoreWithCustomerAddr(String token, String invokeAddress, String currentAccount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("address", currentAccount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getScoreWithCustomerAddr",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("getScoreWithCustomerAddr", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getScoreWithCustomerAddr", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 获取已经购买的物品
     */
    public BaseResult getGoodsByCustomer(String token, String invokeAddress, String currentAccount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("address", currentAccount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getGoodsByCustomer",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("getGoodsByCustomer", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getGoodsByCustomer", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 客户实现任意的积分转让
     */
    public BaseResult transferScoreToAnotherFromCustomer(String token, String invokeAddress, String sender,String receiver, String scoreAmount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[4];
        arrFunParamReal[0] = new FuncParamReal("uint256",0);
        arrFunParamReal[1] = new FuncParamReal("address", sender);
        arrFunParamReal[2] = new FuncParamReal("address", receiver);
        arrFunParamReal[3] = new FuncParamReal("uint256", scoreAmount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "transferScoreToAnother",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("transferScoreToAnother", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("transferScoreToAnother", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 客户购买商品
     */
    public BaseResult buyGood(String token, String invokeAddress, String currentAccount, String goodId) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[2];
        arrFunParamReal[0] = new FuncParamReal("address", currentAccount);
        arrFunParamReal[1] = new FuncParamReal("bytes32", goodId);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "buyGood",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("buyGood", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("buyGood", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 注册商家
     */
    public BaseResult newMerchant(String token, String invokeAddress, String merchantAddress, String merchantPassword) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[2];
        arrFunParamReal[0] = new FuncParamReal("address", merchantAddress);
        arrFunParamReal[1] = new FuncParamReal("bytes32", merchantPassword);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "newMerchant",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("newMerchant", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("newMerchant", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 商家登录
     */
    public BaseResult merchantLogin(String token, String invokeAddress, String merchantLoginAddr, String merchantLoginPwd) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("address", merchantLoginAddr);
       // arrFunParamReal[1] = new FuncParamReal("bytes32", merchantLoginPwd);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getMerchantPassword",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                String data=FunctionDecode.resultDecode("getMerchantPassword", ContractUtils.getAbi(), getTxReciptReturn.getRet());

                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        data);
                /**
                 * 判断密码是否正确
                 */
                JSONArray jsonArray = JSON.parseArray(data);
                JSONObject jsonObject = jsonArray.getJSONObject(1);
                String password=jsonObject.getString("mayvalue");
                if(password.equals(merchantLoginPwd)){
                    //登陆成功
                    baseResult.setMessage("登陆成功");
                }else{
                    baseResult.setCode(Code.INVOKE_FAIL.getCode());
                    baseResult.setMessage("登录失败");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getMerchantPassword", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return baseResult;
    }

    /**
     * 根据商户address获取积分余额
     */
    public BaseResult getScoreWithMerchantAddr(String token, String invokeAddress, String currentAccount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("address", currentAccount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getScoreWithMerchantAddr",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("getScoreWithMerchantAddr", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getScoreWithMerchantAddr", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 商户实现任意的积分转让
     */
    public BaseResult transferScoreToAnotherFromMerchant(String token, String invokeAddress, String sender,String receiver, String scoreAmount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[4];
        arrFunParamReal[0] = new FuncParamReal("uint256", 1);
        arrFunParamReal[1] = new FuncParamReal("address", sender);
        arrFunParamReal[2] = new FuncParamReal("address", receiver);
        arrFunParamReal[3] = new FuncParamReal("uint256", scoreAmount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "transferScoreToAnother",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("transferScoreToAnother", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("transferScoreToAnother", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 商户增加一件商品：默认gas会OOG
     */
    public BaseResult addGood(String token, String invokeAddress, String currentAccount, String goodId, String goodPrice) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[3];
        arrFunParamReal[0] = new FuncParamReal("address", currentAccount);
        arrFunParamReal[1] = new FuncParamReal("bytes32", goodId);
        arrFunParamReal[2] = new FuncParamReal("uint256", goodPrice);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "addGood",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("addGood", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("addGood", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 商户查看已添加的所有商品
     */
    public BaseResult getGoodsByMerchant(String token, String invokeAddress, String currentAccount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("address", currentAccount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "getGoodsByMerchant",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("getGoodsByMerchant", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("getGoodsByMerchant", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 商户和银行进行积分清算
     */
    public BaseResult settleScoreWithBank(String token, String invokeAddress, String currentAccount, String settleAmount) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[2];
        arrFunParamReal[0] = new FuncParamReal("address", currentAccount);
        arrFunParamReal[1] = new FuncParamReal("uint256", settleAmount);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "settleScoreWithBank",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("settleScoreWithBank", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("settleScoreWithBank", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }
}
