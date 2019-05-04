var module1 = new Object({

 isIntNum: function (val)
{
    var regPos = /^[0-9]+.?[0-9]*$/;
    ; // 非负整数
    var regNeg = /^\-[1-9][0-9]*$/; // 负整数 regPos.test(val) || regNeg.test(val)
    var d=/^\d+(\.\d+)?$/
    if (d.test(val) || regNeg.test(val) ) {
        return true;
    } else {
        return false;
    }
}
})
// import module1 from './jstools/mytool' 引用方式
 export default module1