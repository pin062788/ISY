# 错误记录
1.WebSecurity中，获取session中用户信息会为空，导致异常到错误页面，解决该
问题需要对UsersServiceImpl中的获取session用户信息进行返回值处理，但目前
转换工作未进行到此，暂时采取异常常规处理，待完成kotlin转换后进行详细处理。  
2.SecurityLoginFilter中因Users为空产生异常。  
3.MenuInterceptor 因Users为空产生异常。