
contract Score {

    address owner; 
    uint issuedScoreAmount; 
    uint settledScoreAmount; 

    struct Customer {
        address customerAddr; 
        bytes32 password; 
        uint scoreAmount; 
        bytes32[] buyGoods; 
    }

    struct Merchant {
        address merchantAddr; 
        bytes32 password; 
        uint scoreAmount; 
        bytes32[] sellGoods; 
    }

    struct Good {
        bytes32 goodId; 
        uint price; 
        address belong; 
    }


    mapping(address => Customer) customer;
    mapping(address => Merchant) merchant;
    mapping(bytes32 => Good) good; 

    address[] customers; 
    address[] merchants; 
    bytes32[] goods; 

    function Score() public {
        owner = msg.sender;
    }


    function getOwner() constant public  returns (address) {
        return owner;
    }

    function newCustomer(address _customerAddr, bytes32 _password) public {
        if (!isCustomerAlreadyRegister(_customerAddr)) {
            customer[_customerAddr].customerAddr = _customerAddr;
            customer[_customerAddr].password = _password;
            customers.push(_customerAddr);
            return;
        }
        else {
            return;
        }
    }


    function newMerchant(address _merchantAddr,
        bytes32 _password) public {

        if (!isMerchantAlreadyRegister(_merchantAddr)) {
            merchant[_merchantAddr].merchantAddr = _merchantAddr;
            merchant[_merchantAddr].password = _password;
            merchants.push(_merchantAddr);
            return;
        }
        else {
            return;
        }
    }

    function isCustomerAlreadyRegister(address _customerAddr) internal returns (bool)  {
        for (uint i = 0; i < customers.length; i++) {
            if (customers[i] == _customerAddr) {
                return true;
            }
        }
        return false;
    }

    function isMerchantAlreadyRegister(address _merchantAddr) public returns (bool) {
        for (uint i = 0; i < merchants.length; i++) {
            if (merchants[i] == _merchantAddr) {
                return true;
            }
        }
        return false;
    }

    function getCustomerPassword(address _customerAddr) constant public returns (bool, bytes32) {
        if (isCustomerAlreadyRegister(_customerAddr)) {
            return (true, customer[_customerAddr].password);
        }
        else {
            return (false, "");
        }
    }

    function getMerchantPassword(address _merchantAddr) constant public returns (bool, bytes32) {
        if (isMerchantAlreadyRegister(_merchantAddr)) {
            return (true, merchant[_merchantAddr].password);
        }
        else {
            return (false, "");
        }
    }

    function sendScoreToCustomer(address _receiver,
        uint _amount) public {

        if (isCustomerAlreadyRegister(_receiver)) {
            issuedScoreAmount += _amount;
            customer[_receiver].scoreAmount += _amount;
            return;
        }
        else {
            return;
        }
    }

    function getScoreWithCustomerAddr(address customerAddr) constant public returns (uint) {
        return customer[customerAddr].scoreAmount;
    }

    function getScoreWithMerchantAddr(address merchantAddr) constant public returns (uint) {
        return merchant[merchantAddr].scoreAmount;
    }

    function transferScoreToAnother(uint _senderType,
        address _sender,
        address _receiver,
        uint _amount) public {

        if (!isCustomerAlreadyRegister(_receiver) && !isMerchantAlreadyRegister(_receiver)) {

            return;
        }
        if (_senderType == 0) {
            if (customer[_sender].scoreAmount >= _amount) {
                customer[_sender].scoreAmount -= _amount;

                if (isCustomerAlreadyRegister(_receiver)) {
                    customer[_receiver].scoreAmount += _amount;
                } else {
                    merchant[_receiver].scoreAmount += _amount;
                }
                return;
            } else {
                return;
            }
        } else {
            if (merchant[_sender].scoreAmount >= _amount) {
                merchant[_sender].scoreAmount -= _amount;
                if (isCustomerAlreadyRegister(_receiver)) {
                    customer[_receiver].scoreAmount += _amount;
                } else {
                    merchant[_receiver].scoreAmount += _amount;
                }
                return;
            } else {
                return;
            }
        }
    }

    function getIssuedScoreAmount() constant public returns (uint) {
        return issuedScoreAmount;
    }

    function getSettledScoreAmount() constant public returns (uint) {
        return settledScoreAmount;
    }

    function addGood(address _merchantAddr, bytes32 _goodId, uint _price) public {

        if (!isGoodAlreadyAdd(_goodId)) {
            good[_goodId].goodId = _goodId;
            good[_goodId].price = _price;
            good[_goodId].belong = _merchantAddr;

            goods.push(_goodId);
            merchant[_merchantAddr].sellGoods.push(_goodId);
            return;
        }
        else {
            return;
        }
    }

    function getGoodsByMerchant(address _merchantAddr) constant public returns (bytes32[]) {
        return merchant[_merchantAddr].sellGoods;
    }

    function buyGood(address _customerAddr, bytes32 _goodId) public {
        bytes32 tempId = _goodId;
        if (isGoodAlreadyAdd(tempId)) {
            if (customer[_customerAddr].scoreAmount < good[tempId].price) {
                return;
            }
            else {
                customer[_customerAddr].scoreAmount -= good[tempId].price;
                merchant[good[tempId].belong].scoreAmount += good[tempId].price;
                customer[_customerAddr].buyGoods.push(tempId);
                return;
            }
        }
        else {
            return;
        }
    }

    function getGoodsByCustomer(address _customerAddr) constant public returns (bytes32[]) {
        return customer[_customerAddr].buyGoods;
    }

    function isGoodAlreadyAdd(bytes32 _goodId) internal returns (bool) {
        for (uint i = 0; i < goods.length; i++) {
            if (goods[i] == _goodId) {
                return true;
            }
        }
        return false;
    }

    function settleScoreWithBank(address _merchantAddr, uint _amount) public {
        if (merchant[_merchantAddr].scoreAmount >= _amount) {
            merchant[_merchantAddr].scoreAmount -= _amount;
            settledScoreAmount += _amount;
            return;
        }
        else {
            return;
        }
    }
}
