// 
// 
// 

package com.controller;

import com.entity.Liuyan;
import com.entity.Jilu;
import org.springframework.web.multipart.MultipartFile;
import com.entity.Yuding;
import javax.servlet.http.HttpSession;
import com.entity.Vip;
import com.util.Util;
import com.entity.User;
import com.entity.Car;
import com.entity.Gonggao;
import java.util.List;
import com.util.Pager;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import com.service.VipService;
import com.service.LiuyanService;
import com.service.JiluService;
import com.service.YudingService;
import com.service.PicService;
import com.service.CarService;
import com.service.GonggaoService;
import javax.annotation.Resource;
import com.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping({ "/" })
public class IndexController
{
    @Resource
    private UserService userService;
    @Resource
    private GonggaoService gonggaoService;
    @Resource
    private CarService carService;
    @Resource
    private PicService picService;
    @Resource
    private YudingService yudingService;
    @Resource
    private JiluService jiluService;
    @Resource
    private LiuyanService liuyanService;
    @Resource
    private VipService vipService;
    @Value("#{jdbc.pageSize}")
    private int pageSize;
    
    public PrintWriter getPrintWriter(final HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }
    
    @RequestMapping({ "/index.do" })
    public String index(final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("gonggaolist", (Object)this.gonggaoService.selectBeanList(0, 8, null));
        request.setAttribute("carlist", (Object)this.carService.selectBeanList(0, 4, null, null, null, null));
        request.setAttribute("piclist", (Object)this.picService.selectBeanList(0, 99));
        return "index.jsp";
    }
    
    @RequestMapping({ "/gonggaolist.do" })
    public String gonggaolist(final HttpServletRequest request, final String pagenum, final String gbiaoti) {
        if (gbiaoti != null && !"".equals(gbiaoti)) {
            request.setAttribute("gbiaoti", (Object)gbiaoti);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Gonggao> list = this.gonggaoService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, gbiaoti);
        request.setAttribute("list", (Object)list);
        final int total = this.gonggaoService.selectBeanCount(gbiaoti);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "gonggaolist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("title", (Object)"\u7f51\u7ad9\u516c\u544a");
        return "gonggaolist.jsp";
    }
    
    @RequestMapping({ "/gonggaoview.do" })
    public String gonggaoview(final HttpServletRequest request, final int id) {
        final Gonggao bean = this.gonggaoService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u7f51\u7ad9\u516c\u544a\u8be6\u60c5");
        return "gonggaoview.jsp";
    }
    
    @RequestMapping({ "/carlist.do" })
    public String carlist(final HttpServletRequest request, final String pagenum, final String pinpai, final String xinghao, final String yanse) {
        if (pinpai != null && !"".equals(pinpai)) {
            request.setAttribute("pinpai", (Object)pinpai);
        }
        if (xinghao != null && !"".equals(xinghao)) {
            request.setAttribute("xinghao", (Object)xinghao);
        }
        if (yanse != null && !"".equals(yanse)) {
            request.setAttribute("yanse", (Object)yanse);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Car> list = this.carService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, null, pinpai, xinghao, yanse);
        request.setAttribute("list", (Object)list);
        final int total = this.carService.selectBeanCount(null, pinpai, xinghao, yanse);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "carlist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("title", (Object)"\u8f66\u8f86");
        return "carlist.jsp";
    }
    
    @RequestMapping({ "/carview.do" })
    public String carview(final HttpServletRequest request, final int id) {
        final Car bean = this.carService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u8f66\u8f86\u8be6\u60c5");
        return "carview.jsp";
    }
    
    @RequestMapping({ "/register.do" })
    public String register(final HttpServletRequest request) {
        request.setAttribute("title", (Object)"\u7528\u6237\u6ce8\u518c");
        return "register.jsp";
    }
    
    @RequestMapping({ "/register2.do" })
    public void register2(final HttpServletRequest request, final HttpServletResponse response, final User user) {
        final PrintWriter writer = this.getPrintWriter(response);
        User bean = this.userService.useryz(user.getUsername());
        if (bean != null) {
            writer.print("<script language=javascript>alert('\u8be5\u7528\u6237\u540d\u5df2\u7ecf\u5b58\u5728\uff0c\u6ce8\u518c\u5931\u8d25\uff01');window.location.href='register.do';</script>");
            return;
        }
        bean = this.userService.sfzyz(user.getSfz());
        if (bean != null) {
            writer.print("<script language=javascript>alert('\u8be5\u8eab\u4efd\u8bc1\u5df2\u7ecf\u5b58\u5728\uff0c\u6ce8\u518c\u5931\u8d25\uff01');window.location.href='register.do';</script>");
            return;
        }
        user.setCreatetime(Util.getTime());
        user.setRole(4);
        this.userService.insertBean(user);
        writer.print("<script language=javascript>alert('\u6ce8\u518c\u6210\u529f');window.location.href='login.do';</script>");
    }
    
    @RequestMapping({ "/login.do" })
    public String login(final HttpServletRequest request) {
        request.setAttribute("title", (Object)"\u7528\u6237\u767b\u5f55");
        return "login.jsp";
    }
    
    @RequestMapping({ "/login2.do" })
    public void login2(final HttpServletRequest request, final HttpServletResponse response, final String username, final String password) {
        final PrintWriter writer = this.getPrintWriter(response);
        final User bean = this.userService.userlogin(username, password, 4);
        if (bean == null) {
            writer.print("<script language=javascript>alert('\u7528\u6237\u540d\u6216\u8005\u5bc6\u7801\u9519\u8bef\uff0c\u767b\u5f55\u5931\u8d25\uff01');window.location.href='login.do';</script>");
        }
        else {
            final HttpSession session = request.getSession();
            session.setAttribute("qiantai", (Object)bean);
            session.setAttribute("username", (Object)bean.getUsername());
            final Vip vip = this.vipService.selectBeanByUsername(username);
            if (vip != null) {
                session.setAttribute("vip", (Object)vip);
            }
            else {
                final Vip vip2 = new Vip();
                vip2.setUsername(username);
                vip2.setLevel(0);
                vip2.setPoints(0);
                session.setAttribute("vip", (Object)vip2);
            }
            writer.print("<script language=javascript>alert('\u767b\u5f55\u6210\u529f');window.location.href='.';</script>");
        }
    }
    
    @RequestMapping({ "/loginout.do" })
    public void loginout(final HttpServletRequest request, final HttpServletResponse response) {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        session.removeAttribute("qiantai");
        writer.print("<script language=javascript>alert('\u9000\u51fa\u6210\u529f');window.location.href='.';</script>");
    }
    
    @RequestMapping({ "/password.do" })
    public String password(final HttpServletRequest request) {
        request.setAttribute("title", (Object)"\u4fee\u6539\u5bc6\u7801");
        return "password.jsp";
    }
    
    @RequestMapping({ "/password2.do" })
    public void password2(final HttpServletRequest request, final HttpServletResponse response, final String password1, final String password2) {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("qiantai");
        final User bean = this.userService.userlogin(user.getUsername(), password1, 4);
        if (bean == null) {
            writer.print("<script language=javascript>alert('\u539f\u5bc6\u7801\u9519\u8bef\uff0c\u4fee\u6539\u5931\u8d25\uff01');window.location.href='password.do';</script>");
        }
        else {
            bean.setPassword(password2);
            this.userService.updateBean(bean);
            writer.print("<script language=javascript>alert('\u4fee\u6539\u6210\u529f,\u8bf7\u91cd\u65b0\u767b\u5f55');window.location.href='loginout.do';</script>");
        }
    }
    
    @RequestMapping({ "/userupdate.do" })
    public String userupdate(final HttpServletRequest request) {
        request.setAttribute("title", (Object)"\u4e2a\u4eba\u4fe1\u606f\u7ef4\u62a4");
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("qiantai");
        final User bean = this.userService.selectBeanById(user.getId());
        request.setAttribute("bean", (Object)bean);
        return "userupdate.jsp";
    }
    
    @RequestMapping({ "/userupdate2.do" })
    public void userupdate2(final HttpServletRequest request, final HttpServletResponse response, final String name, final String tej) {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("qiantai");
        final User bean = this.userService.selectBeanById(user.getId());
        bean.setTej(tej);
        bean.setName(name);
        this.userService.updateBean(bean);
        writer.print("<script language=javascript>alert('\u4fee\u6539\u6210\u529f');window.location.href='userupdate.do';</script>");
    }
    
    @RequestMapping({ "/yudingadd.do" })
    public String yudingadd(final HttpServletRequest request, final HttpServletResponse response, final int carid) {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        User user = (User)session.getAttribute("qiantai");
        if (user == null) {
            writer.print("<script  language='javascript'>alert('\u8bf7\u5148\u767b\u5f55');window.location.href='login.do';</script>");
            return null;
        }
        final Car car = this.carService.selectBeanById(carid);
        request.setAttribute("car", (Object)car);
        user = this.userService.selectBeanById(user.getId());
        request.setAttribute("user", (Object)user);
        request.setAttribute("url", (Object)("yudingadd2.do?carid=" + carid));
        request.setAttribute("title", (Object)"\u9884\u5b9a\u79df\u8f66");
        return "yudingadd.jsp";
    }
    
    @RequestMapping({ "/yudingadd2.do" })
    public void yudingadd2(final HttpServletRequest request, final HttpServletResponse response, final Yuding bean, final MultipartFile prodFile1, final MultipartFile prodFile2) throws IOException {
        final PrintWriter writer = this.getPrintWriter(response);
        final Car car = this.carService.selectBeanById(bean.getCarid());
        final int carid = car.getId();
        if (prodFile1 == null || prodFile1.getSize() <= 0L) {
            this.getPrintWriter(response).print("<script language=javascript>alert('\u8eab\u4efd\u8bc1\u590d\u5370\u4ef6\u4e0d\u80fd\u4e3a\u7a7a');window.location.href='yudingadd.do?carid=" + carid + "';</script>");
            return;
        }
        if (prodFile2 == null || prodFile2.getSize() <= 0L) {
            this.getPrintWriter(response).print("<script language=javascript>alert('\u9a7e\u9a76\u8bc1\u590d\u5370\u4ef6\u4e0d\u80fd\u4e3a\u7a7a');window.location.href='yudingadd.do?carid=" + carid + "';</script>");
            return;
        }
        final HttpSession session = request.getSession();
        User user = (User)session.getAttribute("qiantai");
        bean.setZhuangtai("\u5904\u7406\u4e2d");
        bean.setShijian1(Util.getTime());
        bean.setChepai(car.getChepai());
        bean.setKehuid(user.getId());
        user = this.userService.selectBeanById(user.getId());
        bean.setSfz(user.getSfz());
        final String sfz2 = Util.uploadFile(request, prodFile1);
        final String jsz = Util.uploadFile(request, prodFile2);
        bean.setSfz2(sfz2);
        bean.setJsz(jsz);
        this.yudingService.insertBean(bean);
        car.setStatus("\u9884\u5b9a\u4e2d");
        this.carService.updateBean(car);
        writer.print("<script  language='javascript'>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='yudinglist.do'; </script>");
    }
    
    @RequestMapping({ "/yudinglist.do" })
    public String yudinglist(final HttpServletRequest request, final String pagenum, final String chepai) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("qiantai");
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Yuding> list = this.yudingService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, null, user.getId(), 0, null);
        request.setAttribute("list", (Object)list);
        final int total = this.yudingService.selectBeanCount(chepai, null, user.getId(), 0, null);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "yudinglist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("title", (Object)"\u6211\u7684\u9884\u5b9a");
        return "yudinglist.jsp";
    }
    
    @RequestMapping({ "/yudingview.do" })
    public String yudingview(final HttpServletRequest request, final int id) {
        final Yuding bean = this.yudingService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u9884\u5b9a\u8be6\u60c5");
        return "yudingview.jsp";
    }
    
    @RequestMapping({ "/yudingupdate2.do" })
    public void yudingupdate2(final HttpServletRequest request, final HttpServletResponse response, final int id) throws IOException {
        final PrintWriter writer = this.getPrintWriter(response);
        final Yuding yuding = this.yudingService.selectBeanById(id);
        yuding.setZhuangtai("\u5df2\u53d6\u6d88");
        this.yudingService.updateBean(yuding);
        final Car car = this.carService.selectBeanById(yuding.getCarid());
        car.setStatus("\u5f85\u79df\u8d41");
        this.carService.updateBean(car);
        writer.print("<script  language='javascript'>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='yudinglist.do'; </script>");
    }
    
    @RequestMapping({ "/yudingdelete.do" })
    public void yudingdelete(final HttpServletRequest request, final HttpServletResponse response, final int id) throws IOException {
        final PrintWriter writer = this.getPrintWriter(response);
        this.yudingService.deleteBean(id);
        writer.print("<script  language='javascript'>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='yudinglist.do'; </script>");
    }
    
    @RequestMapping({ "/jilulist.do" })
    public String jilulist(final HttpServletRequest request, final String pagenum, final String chepai) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("qiantai");
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Jilu> list = this.jiluService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, null, null, null, 0, 0, user.getId());
        request.setAttribute("list", (Object)list);
        final int total = this.jiluService.selectBeanCount(chepai, null, null, null, 0, 0, user.getId());
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "jilulist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("title", (Object)"\u6211\u7684\u79df\u8f66");
        return "jilulist.jsp";
    }
    
    @RequestMapping({ "/jiluview.do" })
    public String jiluview(final HttpServletRequest request, final int id) {
        final Jilu bean = this.jiluService.selectBeanById(id);
        final Car car = this.carService.selectBeanById(bean.getCarid());
        bean.setCar(car);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u79df\u8f66\u8bb0\u5f55\u8be6\u60c5");
        return "jiluview.jsp";
    }
    
    @RequestMapping({ "/liuyanadd.do" })
    public String liuyanadd(final HttpServletRequest request, final HttpServletResponse response) {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("qiantai");
        if (user == null) {
            writer.print("<script  language='javascript'>alert('\u8bf7\u5148\u767b\u5f55');window.location.href='login.do';</script>");
            return null;
        }
        request.setAttribute("url", (Object)"liuyanadd2.do");
        request.setAttribute("title", (Object)"\u7559\u8a00");
        return "liuyanadd.jsp";
    }
    
    @RequestMapping({ "/liuyanadd2.do" })
    public void liuyanadd2(final HttpServletRequest request, final HttpServletResponse response, final Liuyan bean) throws IOException {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        User user = (User)session.getAttribute("qiantai");
        user = this.userService.selectBeanById(user.getId());
        bean.setKehuid(user.getId());
        bean.setName(user.getName());
        bean.setShijian1(Util.getTime());
        bean.setZhuangtai("\u672a\u56de\u590d");
        this.liuyanService.insertBean(bean);
        writer.print("<script  language='javascript'>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='liuyanlist.do'; </script>");
    }
    
    @RequestMapping({ "/liuyanlist.do" })
    public String liuyanlist(final HttpServletRequest request, final String pagenum, final String ltitle) {
        if (ltitle != null && !"".equals(ltitle)) {
            request.setAttribute("ltitle", (Object)ltitle);
        }
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("qiantai");
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Liuyan> list = this.liuyanService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, ltitle, null, user.getId(), 0);
        request.setAttribute("list", (Object)list);
        final int total = this.liuyanService.selectBeanCount(ltitle, null, user.getId(), 0);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "liuyanlist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("title", (Object)"\u6211\u7684\u7559\u8a00");
        return "liuyanlist.jsp";
    }
    
    @RequestMapping({ "/liuyanview.do" })
    public String liuyanview(final HttpServletRequest request, final int id) {
        final Liuyan bean = this.liuyanService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u7559\u8a00\u8be6\u60c5");
        return "liuyanview.jsp";
    }
    
    @RequestMapping({ "/liuyandelete.do" })
    public void liuyandelete(final HttpServletRequest request, final HttpServletResponse response, final int id) throws IOException {
        final PrintWriter writer = this.getPrintWriter(response);
        this.liuyanService.deleteBean(id);
        writer.print("<script  language='javascript'>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='liuyanlist.do'; </script>");
    }
    
    @RequestMapping({ "/vip.do" })
    public String vip(final HttpServletRequest request, final String username) {
        final Vip bean = this.vipService.selectBeanByUsername(username);
        if (bean != null) {
            bean.setEnddate(bean.getEnddate().split(" ")[0]);
            request.setAttribute("bean", (Object)bean);
        }
        else {
            final Vip bean2 = new Vip();
            bean2.setUsername(username);
            request.setAttribute("bean", (Object)bean2);
        }
        request.setAttribute("title", (Object)"\u4f1a\u5458\u8be6\u60c5");
        return "vip.jsp";
    }
}
