// 
// 
// 

package com.controller;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.entity.Yue;
import com.entity.Ri;
import com.entity.Liuyan;
import com.entity.Yuding;
import java.text.ParseException;
import com.entity.Vip;
import java.util.Date;
import java.util.Iterator;
import com.entity.Jilu;
import com.entity.Pic;
import com.entity.Gonggao;
import org.springframework.web.multipart.MultipartFile;
import com.entity.Car;
import com.util.Util;
import java.util.List;
import com.util.Pager;
import javax.servlet.http.HttpSession;
import com.entity.User;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import com.service.VipService;
import com.service.YueService;
import com.service.RiService;
import com.service.LiuyanService;
import com.service.YudingService;
import com.service.JiluService;
import com.service.PicService;
import com.service.GonggaoService;
import com.service.CarService;
import javax.annotation.Resource;
import com.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping({ "/manage" })
public class ManageController
{
    @Resource
    private UserService userService;
    @Resource
    private CarService carService;
    @Resource
    private GonggaoService gonggaoService;
    @Resource
    private PicService picService;
    @Resource
    private JiluService jiluService;
    @Resource
    private YudingService yudingService;
    @Resource
    private LiuyanService liuyanService;
    @Resource
    private RiService riService;
    @Resource
    private YueService yueService;
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
    
    @RequestMapping({ "/login.do" })
    public void login(final HttpServletRequest request, final HttpServletResponse response, final String username, final String password, final String role) {
        final PrintWriter writer = this.getPrintWriter(response);
        final User user = this.userService.userlogin(username, password, Integer.parseInt(role));
        if (user == null) {
            writer.print("<script language=javascript>alert('\u7528\u6237\u540d\u6216\u8005\u5bc6\u7801\u9519\u8bef');window.location.href='login.jsp';</script>");
        }
        else {
            final HttpSession session = request.getSession();
            session.setAttribute("manage", (Object)user);
            writer.print("<script language=javascript>alert('\u767b\u5f55\u6210\u529f');window.location.href='index.jsp';</script>");
        }
    }
    
    @RequestMapping({ "/loginout.do" })
    public void loginout(final HttpServletRequest request, final HttpServletResponse response, final String username, final String password) {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        session.removeAttribute("manage");
        writer.print("<script language=javascript>alert('\u9000\u51fa\u6210\u529f');window.location.href='login.jsp';</script>");
    }
    
    @RequestMapping({ "/password.do" })
    public String password(final HttpServletRequest request) {
        request.setAttribute("url", (Object)"password2.do");
        request.setAttribute("title", (Object)"\u4fee\u6539\u5bc6\u7801");
        return "password.jsp";
    }
    
    @RequestMapping({ "/password2.do" })
    public void password2(final HttpServletRequest request, final HttpServletResponse response, final String password1, final String password2) {
        final PrintWriter writer = this.getPrintWriter(response);
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        final User bean = this.userService.userlogin(user.getUsername(), password1, user.getRole());
        if (bean != null) {
            bean.setPassword(password2);
            this.userService.updateBean(bean);
            writer.print("<script language=javascript>alert('\u4fee\u6539\u6210\u529f');window.location.href='password.do';</script>");
        }
        else {
            writer.print("<script language=javascript>alert('\u7528\u6237\u540d\u6216\u8005\u5bc6\u7801\u9519\u8bef');window.location.href='password.do';</script>");
        }
    }
    
    @RequestMapping({ "/userlist.do" })
    public String userlist(final HttpServletRequest request, final String pagenum, final String username) {
        if (username != null && !"".equals(username)) {
            request.setAttribute("username", (Object)username);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<User> list = this.userService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, username);
        request.setAttribute("list", (Object)list);
        final int total = this.userService.selectBeanCount(username);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "userlist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"userlist.do");
        request.setAttribute("url2", (Object)"user");
        request.setAttribute("title", (Object)"\u4eba\u5458\u7ba1\u7406");
        return "user/userlist.jsp";
    }
    
    @RequestMapping({ "/useradd.do" })
    public String useradd(final HttpServletRequest request) {
        request.setAttribute("url", (Object)"useradd2.do");
        request.setAttribute("title", (Object)"\u6dfb\u52a0\u4eba\u5458");
        return "user/useradd.jsp";
    }
    
    @RequestMapping({ "/useradd2.do" })
    public void useradd2(final HttpServletResponse response, final User bean) {
        final User user = this.userService.useryz(bean.getUsername());
        if (user != null) {
            this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u5931\u8d25\uff0c\u8be5\u7528\u6237\u540d\u5df2\u7ecf\u5b58\u5728');window.location.href='userlist.do';</script>");
            return;
        }
        bean.setPassword("111111");
        bean.setCreatetime(Util.getTime());
        this.userService.insertBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='userlist.do';</script>");
    }
    
    @RequestMapping({ "/userupdate.do" })
    public String userupdate(final HttpServletRequest request, final int id) {
        final User bean = this.userService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("userupdate2.do?id=" + id));
        request.setAttribute("title", (Object)"\u4fee\u6539\u4eba\u5458");
        return "user/userupdate.jsp";
    }
    
    @RequestMapping({ "/userupdate2.do" })
    public void userupdate2(final HttpServletResponse response, final User bean) {
        this.userService.updateBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='userlist.do';</script>");
    }
    
    @RequestMapping({ "/userdelete.do" })
    public void userdelete(final HttpServletResponse response, final int id) {
        this.userService.deleteBean(id);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='userlist.do';</script>");
    }
    
    @RequestMapping({ "/userupdate3.do" })
    public String userupdate3(final HttpServletRequest request, final int id) {
        final User bean = this.userService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u67e5\u770b\u8be6\u60c5");
        return "user/userupdate3.jsp";
    }
    
    @RequestMapping({ "/carlist.do" })
    public String carlist(final HttpServletRequest request, final String pagenum, final String chepai) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Car> list = this.carService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, null, null, null);
        request.setAttribute("list", (Object)list);
        final int total = this.carService.selectBeanCount(chepai, null, null, null);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "carlist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"carlist.do");
        request.setAttribute("url2", (Object)"car");
        request.setAttribute("title", (Object)"\u8f66\u8f86\u7ba1\u7406");
        return "car/carlist.jsp";
    }
    
    @RequestMapping({ "/caradd.do" })
    public String caradd(final HttpServletRequest request) {
        request.setAttribute("url", (Object)"caradd2.do");
        request.setAttribute("title", (Object)"\u6dfb\u52a0\u8f66\u8f86");
        return "car/caradd.jsp";
    }
    
    @RequestMapping({ "/caradd2.do" })
    public void caradd2(final HttpServletResponse response, final HttpServletRequest request, final Car bean, final MultipartFile prodFile) {
        if (prodFile == null || prodFile.getSize() <= 0L) {
            this.getPrintWriter(response).print("<script language=javascript>alert('\u8f66\u8f86\u56fe\u7247\u4e0d\u80fd\u4e3a\u7a7a');window.location.href='caradd.do';</script>");
            return;
        }
        final String pic = Util.uploadFile(request, prodFile);
        bean.setPic(pic);
        bean.setStatus("\u5f85\u79df\u8d41");
        bean.setCtime(Util.getTime());
        this.carService.insertBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='carlist.do';</script>");
    }
    
    @RequestMapping({ "/carupdate.do" })
    public String carupdate(final HttpServletRequest request, final int id) {
        final Car bean = this.carService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("carupdate2.do?id=" + id));
        request.setAttribute("title", (Object)"\u4fee\u6539\u8f66\u8f86");
        return "car/carupdate.jsp";
    }
    
    @RequestMapping({ "/carupdate2.do" })
    public void carupdate2(final HttpServletResponse response, final HttpServletRequest request, final Car bean, final MultipartFile prodFile) {
        if (prodFile != null && prodFile.getSize() > 0L) {
            final String pic = Util.uploadFile(request, prodFile);
            bean.setPic(pic);
        }
        this.carService.updateBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='carlist.do';</script>");
    }
    
    @RequestMapping({ "/cardelete.do" })
    public void cardelete(final HttpServletResponse response, final int id) {
        this.carService.deleteBean(id);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='carlist.do';</script>");
    }
    
    @RequestMapping({ "/carupdate3.do" })
    public String carupdate3(final HttpServletRequest request, final int id) {
        final Car bean = this.carService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u67e5\u770b\u8f66\u8f86\u8be6\u60c5");
        return "car/carupdate3.jsp";
    }
    
    @RequestMapping({ "/carlist2.do" })
    public String carlist2(final HttpServletRequest request, final String pagenum, final String chepai) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Car> list = this.carService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, null, null, null);
        request.setAttribute("list", (Object)list);
        final int total = this.carService.selectBeanCount(chepai, null, null, null);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "carlist2.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"carlist2.do");
        request.setAttribute("url2", (Object)"car");
        request.setAttribute("title", (Object)"\u8f66\u8f86\u7ef4\u4fee\u7ba1\u7406");
        return "car/carlist2.jsp";
    }
    
    @RequestMapping({ "/cardelete2.do" })
    public void cardelete2(final HttpServletResponse response, final int id) {
        final Car bean = this.carService.selectBeanById(id);
        bean.setStatus("\u7ef4\u4fee\u4e2d");
        this.carService.updateBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='carlist2.do';</script>");
    }
    
    @RequestMapping({ "/cardelete3.do" })
    public void cardelete3(final HttpServletResponse response, final int id) {
        final Car bean = this.carService.selectBeanById(id);
        bean.setStatus("\u5f85\u79df\u8d41");
        this.carService.updateBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='carlist2.do';</script>");
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
        request.setAttribute("url", (Object)"gonggaolist.do");
        request.setAttribute("url2", (Object)"gonggao");
        request.setAttribute("title", (Object)"\u7f51\u7ad9\u516c\u544a\u7ba1\u7406");
        return "gonggao/gonggaolist.jsp";
    }
    
    @RequestMapping({ "/gonggaoadd.do" })
    public String gonggaoadd(final HttpServletRequest request) {
        request.setAttribute("url", (Object)"gonggaoadd2.do");
        request.setAttribute("title", (Object)"\u6dfb\u52a0\u7f51\u7ad9\u516c\u544a");
        return "gonggao/gonggaoadd.jsp";
    }
    
    @RequestMapping({ "/gonggaoadd2.do" })
    public void gonggaoadd2(final HttpServletResponse response, final Gonggao bean) {
        bean.setCtime(Util.getTime());
        this.gonggaoService.insertBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='gonggaolist.do';</script>");
    }
    
    @RequestMapping({ "/gonggaoupdate.do" })
    public String gonggaoupdate(final HttpServletRequest request, final int id) {
        final Gonggao bean = this.gonggaoService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("gonggaoupdate2.do?id=" + id));
        request.setAttribute("title", (Object)"\u4fee\u6539\u7f51\u7ad9\u516c\u544a");
        return "gonggao/gonggaoupdate.jsp";
    }
    
    @RequestMapping({ "/gonggaoupdate2.do" })
    public void gonggaoupdate2(final HttpServletResponse response, final Gonggao bean) {
        this.gonggaoService.updateBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='gonggaolist.do';</script>");
    }
    
    @RequestMapping({ "/gonggaodelete.do" })
    public void gonggaodelete(final HttpServletResponse response, final int id) {
        this.gonggaoService.deleteBean(id);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='gonggaolist.do';</script>");
    }
    
    @RequestMapping({ "/gonggaoupdate3.do" })
    public String gonggaoupdate3(final HttpServletRequest request, final int id) {
        final Gonggao bean = this.gonggaoService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u67e5\u770b\u8be6\u60c5");
        return "gonggao/gonggaoupdate3.jsp";
    }
    
    @RequestMapping({ "/piclist.do" })
    public String piclist(final HttpServletRequest request, final String pagenum) {
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Pic> list = this.picService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize);
        request.setAttribute("list", (Object)list);
        final int total = this.picService.selectBeanCount();
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "piclist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"piclist.do");
        request.setAttribute("url2", (Object)"pic");
        request.setAttribute("title", (Object)"\u5c55\u793a\u56fe\u7247\u7ba1\u7406");
        return "pic/piclist.jsp";
    }
    
    @RequestMapping({ "/picupdate.do" })
    public String picupdate(final HttpServletRequest request, final int id) {
        final Pic bean = this.picService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("picupdate2.do?id=" + id));
        request.setAttribute("title", (Object)"\u4fee\u6539\u5c55\u793a\u56fe\u7247");
        return "pic/picupdate.jsp";
    }
    
    @RequestMapping({ "/picupdate2.do" })
    public void picupdate2(final HttpServletResponse response, final HttpServletRequest request, final Pic bean, final MultipartFile prodFile) {
        if (prodFile != null && prodFile.getSize() > 0L) {
            final String pic = Util.uploadFile(request, prodFile);
            bean.setPath(pic);
        }
        this.picService.updateBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='piclist.do';</script>");
    }
    
    @RequestMapping({ "/carlist3.do" })
    public String carlist3(final HttpServletRequest request, final String pagenum, final String chepai, final String pinpai, final String xinghao, final String yanse) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
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
        final List<Car> list = this.carService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, pinpai, xinghao, yanse);
        request.setAttribute("list", (Object)list);
        final int total = this.carService.selectBeanCount(chepai, pinpai, xinghao, yanse);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "carlist3.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"carlist3.do");
        request.setAttribute("url2", (Object)"car");
        request.setAttribute("title", (Object)"\u8f66\u8f86\u79df\u8d41\u7ba1\u7406");
        return "car/carlist3.jsp";
    }
    
    @RequestMapping({ "/jiluadd.do" })
    public String jiluadd(final HttpServletRequest request, final int carid) {
        final Car car = this.carService.selectBeanById(carid);
        request.setAttribute("car", (Object)car);
        request.setAttribute("url", (Object)("jiluadd2.do?carid=" + carid));
        request.setAttribute("title", (Object)"\u79df\u8f66");
        return "jilu/jiluadd.jsp";
    }
    
    @RequestMapping({ "/jiluadd2.do" })
    public void jiluadd2(final HttpServletResponse response, final HttpServletRequest request, final Jilu bean, final MultipartFile prodFile1, final MultipartFile prodFile2) {
        if (prodFile1 == null || prodFile1.getSize() <= 0L) {
            this.getPrintWriter(response).print("<script language=javascript>alert('\u8eab\u4efd\u8bc1\u590d\u5370\u4ef6\u4e0d\u80fd\u4e3a\u7a7a');window.location.href='jiluadd.do';</script>");
            return;
        }
        if (prodFile2 == null || prodFile2.getSize() <= 0L) {
            this.getPrintWriter(response).print("<script language=javascript>alert('\u9a7e\u9a76\u8bc1\u590d\u5370\u4ef6\u4e0d\u80fd\u4e3a\u7a7a');window.location.href='jiluadd.do';</script>");
            return;
        }
        final int carid = bean.getCarid();
        final Car car = this.carService.selectBeanById(carid);
        bean.setChepai(car.getChepai());
        bean.setCarid(car.getId());
        final String sfz = bean.getSfz();
        User kehu = this.userService.sfzyz(sfz);
        if (kehu == null) {
            kehu = new User();
            kehu.setCreatetime(Util.getTime());
            kehu.setName(bean.getXingming());
            kehu.setPassword("111111");
            kehu.setRole(4);
            kehu.setSfz(sfz);
            kehu.setTej(bean.getDianhua());
            kehu.setUsername(sfz);
            this.userService.insertBean(kehu);
        }
        bean.setKehuid(kehu.getId());
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        bean.setWorkid(user.getId());
        final String sfz2 = Util.uploadFile(request, prodFile1);
        final String jsz = Util.uploadFile(request, prodFile2);
        bean.setSfz2(sfz2);
        bean.setJsz(jsz);
        bean.setShijian1(Util.getTime());
        bean.setZhuangtai("\u51fa\u79df\u4e2d");
        this.jiluService.insertBean(bean);
        car.setStatus("\u79df\u8d41\u4e2d");
        this.carService.updateBean(car);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='jilulist.do';</script>");
    }
    
    @RequestMapping({ "/jilulist.do" })
    public String jilulist(final HttpServletRequest request, final String pagenum, final String chepai, final String xingming, final String sfz) {
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        if (xingming != null && !"".equals(xingming)) {
            request.setAttribute("xingming", (Object)xingming);
        }
        if (sfz != null && !"".equals(sfz)) {
            request.setAttribute("sfz", (Object)sfz);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Jilu> list = this.jiluService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, sfz, xingming, null, user.getId(), 0, 0);
        for (final Jilu jilu : list) {
            jilu.setCar(this.carService.selectBeanById(jilu.getCarid()));
        }
        request.setAttribute("list", (Object)list);
        final int total = this.jiluService.selectBeanCount(chepai, sfz, xingming, null, user.getId(), 0, 0);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "jilulist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"jilulist.do");
        request.setAttribute("url2", (Object)"jilu");
        request.setAttribute("title", (Object)"\u8fd8\u8f66\u7ba1\u7406");
        return "jilu/jilulist.jsp";
    }
    
    @RequestMapping({ "/jiluupdate.do" })
    public String jiluupdate(final HttpServletRequest request, final int id) throws ParseException {
        final Jilu bean = this.jiluService.selectBeanById(id);
        final Car car = this.carService.selectBeanById(bean.getCarid());
        bean.setCar(car);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("jiluupdate2.do?id=" + id));
        request.setAttribute("title", (Object)"\u5ba2\u6237\u8fd8\u8f66");
        final long t1 = new Date().getTime();
        final long t2 = Util.parseTime(bean.getShijian1()).getTime();
        final long t3 = 86400000L;
        final long tianshu = (t1 - t2) / t3 + 1L;
        final long tzujin = tianshu * car.getZujin();
        final Vip vip = this.vipService.selectBeanByKehuid(bean.getKehuid());
        double tzujin2 = 0.0;
        if (vip != null) {
            tzujin2 = tianshu * car.getZujin() * vip.getDiscount();
        }
        else {
            tzujin2 = tzujin;
        }
        request.setAttribute("tzujinbefore", (Object)tzujin);
        request.setAttribute("tianshu", (Object)tianshu);
        request.setAttribute("tzujin", (Object)tzujin2);
        request.setAttribute("shijian2", (Object)Util.getTime());
        return "jilu/jiluupdate.jsp";
    }
    
    @RequestMapping({ "/jiluupdate2.do" })
    public void jiluupdate2(final HttpServletResponse response, final HttpServletRequest request) {
        final String id = request.getParameter("id");
        final String shijian2 = request.getParameter("shijian2");
        final String tianshu = request.getParameter("tianshu");
        final String tzujin = request.getParameter("tzujin");
        final Jilu jilu = this.jiluService.selectBeanById(Integer.parseInt(id));
        jilu.setShijian2(shijian2);
        jilu.setTianshu(Integer.parseInt(tianshu));
        jilu.setTzujin(Double.parseDouble(tzujin));
        jilu.setZhuangtai("\u8fd8\u8f66\u4e2d,\u7b49\u5f85\u6280\u672f\u4eba\u5458\u786e\u8ba4");
        this.jiluService.updateBean(jilu);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='jilulist.do';</script>");
    }
    
    @RequestMapping({ "/jiluupdate3.do" })
    public String jiluupdate3(final HttpServletRequest request, final int id) {
        final Jilu bean = this.jiluService.selectBeanById(id);
        bean.setCar(this.carService.selectBeanById(bean.getCarid()));
        bean.setWork(this.userService.selectBeanById(bean.getWorkid()));
        bean.setJishu(this.userService.selectBeanById(bean.getJishuid()));
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u67e5\u770b\u79df\u8f66\u8bb0\u5f55\u8be6\u60c5");
        return "jilu/jiluupdate3.jsp";
    }
    
    @RequestMapping({ "/jilulist2.do" })
    public String jilulist2(final HttpServletRequest request, final String pagenum, final String chepai, final String xingming, final String sfz) {
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        if (xingming != null && !"".equals(xingming)) {
            request.setAttribute("xingming", (Object)xingming);
        }
        if (sfz != null && !"".equals(sfz)) {
            request.setAttribute("sfz", (Object)sfz);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Jilu> list = this.jiluService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, sfz, xingming, "\u8fd8\u8f66\u4e2d,\u7b49\u5f85\u6280\u672f\u4eba\u5458\u786e\u8ba4", user.getId(), 0, 0);
        for (final Jilu jilu : list) {
            jilu.setCar(this.carService.selectBeanById(jilu.getCarid()));
        }
        request.setAttribute("list", (Object)list);
        final int total = this.jiluService.selectBeanCount(chepai, sfz, xingming, "\u8fd8\u8f66\u4e2d,\u7b49\u5f85\u6280\u672f\u4eba\u5458\u786e\u8ba4", user.getId(), 0, 0);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "jilulist2.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"jilulist2.do");
        request.setAttribute("url2", (Object)"jilu");
        request.setAttribute("title", (Object)"\u6280\u672f\u786e\u8ba4\u67e5\u8be2");
        return "jilu/jilulist2.jsp";
    }
    
    @RequestMapping({ "/jilulist3.do" })
    public String jilulist3(final HttpServletRequest request, final String pagenum, final String chepai, final String xingming, final String sfz) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        if (xingming != null && !"".equals(xingming)) {
            request.setAttribute("xingming", (Object)xingming);
        }
        if (sfz != null && !"".equals(sfz)) {
            request.setAttribute("sfz", (Object)sfz);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Jilu> list = this.jiluService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, sfz, xingming, "\u8fd8\u8f66\u4e2d,\u7b49\u5f85\u6280\u672f\u4eba\u5458\u786e\u8ba4", 0, 0, 0);
        for (final Jilu jilu : list) {
            jilu.setCar(this.carService.selectBeanById(jilu.getCarid()));
        }
        request.setAttribute("list", (Object)list);
        final int total = this.jiluService.selectBeanCount(chepai, sfz, xingming, "\u8fd8\u8f66\u4e2d,\u7b49\u5f85\u6280\u672f\u4eba\u5458\u786e\u8ba4", 0, 0, 0);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "jilulist3.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"jilulist3.do");
        request.setAttribute("url2", (Object)"jilu");
        request.setAttribute("title", (Object)"\u5f85\u786e\u8ba4\u8f66\u8f86");
        return "jilu/jilulist3.jsp";
    }
    
    @RequestMapping({ "/jiluupdate5.do" })
    public String jiluupdate5(final HttpServletRequest request, final int id) throws ParseException {
        final Jilu bean = this.jiluService.selectBeanById(id);
        final Car car = this.carService.selectBeanById(bean.getCarid());
        bean.setCar(car);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("jiluupdate6.do?id=" + id));
        request.setAttribute("title", (Object)"\u8f93\u5165\u786e\u8ba4\u4fe1\u606f");
        return "jilu/jiluupdate5.jsp";
    }
    
    @RequestMapping({ "/jiluupdate6.do" })
    public void jiluupdate6(final HttpServletResponse response, final HttpServletRequest request, final Jilu bean) {
        final Jilu jilu = this.jiluService.selectBeanById(bean.getId());
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        jilu.setWeixiu(bean.getWeixiu());
        jilu.setJishuid(user.getId());
        jilu.setZhuangtai("\u6280\u672f\u4eba\u5458\u5df2\u786e\u8ba4");
        this.jiluService.updateBean(jilu);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='jilulist3.do';</script>");
    }
    
    @RequestMapping({ "/jilulist4.do" })
    public String jilulist4(final HttpServletRequest request, final String pagenum, final String chepai, final String xingming, final String sfz) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        if (xingming != null && !"".equals(xingming)) {
            request.setAttribute("xingming", (Object)xingming);
        }
        if (sfz != null && !"".equals(sfz)) {
            request.setAttribute("sfz", (Object)sfz);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        final List<Jilu> list = this.jiluService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, sfz, xingming, null, 0, user.getId(), 0);
        for (final Jilu jilu : list) {
            jilu.setCar(this.carService.selectBeanById(jilu.getCarid()));
        }
        request.setAttribute("list", (Object)list);
        final int total = this.jiluService.selectBeanCount(chepai, sfz, xingming, null, 0, user.getId(), 0);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "jilulist4.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"jilulist4.do");
        request.setAttribute("url2", (Object)"jilu");
        request.setAttribute("title", (Object)"\u6211\u7684\u786e\u8ba4\u8f66\u8f86");
        return "jilu/jilulist4.jsp";
    }
    
    @RequestMapping({ "/jilulist5.do" })
    public String jilulist5(final HttpServletRequest request, final String pagenum, final String chepai, final String xingming, final String sfz) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        if (xingming != null && !"".equals(xingming)) {
            request.setAttribute("xingming", (Object)xingming);
        }
        if (sfz != null && !"".equals(sfz)) {
            request.setAttribute("sfz", (Object)sfz);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        final List<Jilu> list = this.jiluService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, sfz, xingming, "\u6280\u672f\u4eba\u5458\u5df2\u786e\u8ba4", user.getId(), 0, 0);
        for (final Jilu jilu : list) {
            jilu.setCar(this.carService.selectBeanById(jilu.getCarid()));
        }
        request.setAttribute("list", (Object)list);
        final int total = this.jiluService.selectBeanCount(chepai, sfz, xingming, "\u6280\u672f\u4eba\u5458\u5df2\u786e\u8ba4", user.getId(), 0, 0);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "jilulist5.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"jilulist5.do");
        request.setAttribute("url2", (Object)"jilu");
        request.setAttribute("title", (Object)"\u786e\u8ba4\u7ba1\u7406");
        return "jilu/jilulist5.jsp";
    }
    
    @RequestMapping({ "/jiluupdate7.do" })
    public void jiluupdate7(final HttpServletResponse response, final HttpServletRequest request) {
        final String id = request.getParameter("id");
        final Jilu jilu = this.jiluService.selectBeanById(Integer.parseInt(id));
        jilu.setZhuangtai("\u5b8c\u6210\u7ed3\u7b97");
        this.jiluService.updateBean(jilu);
        final Car car = this.carService.selectBeanById(jilu.getCarid());
        car.setStatus("\u5f85\u79df\u8d41");
        car.setWeihu(car.getWeihu() + jilu.getWeixiu());
        car.setTzujin(car.getTzujin() + jilu.getTzujin());
        this.carService.updateBean(car);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='jilulist5.do';</script>");
    }
    
    @RequestMapping({ "/yudinglist.do" })
    public String yudinglist(final HttpServletRequest request, final String pagenum, final String chepai, final String sfz) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        if (sfz != null && !"".equals(sfz)) {
            request.setAttribute("sfz", (Object)sfz);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Yuding> list = this.yudingService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, sfz, 0, 0, "\u5904\u7406\u4e2d");
        request.setAttribute("list", (Object)list);
        final int total = this.yudingService.selectBeanCount(chepai, sfz, 0, 0, "\u5904\u7406\u4e2d");
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "yudinglist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"yudinglist.do");
        request.setAttribute("url2", (Object)"yuding");
        request.setAttribute("title", (Object)"\u5f85\u5904\u7406\u9884\u5b9a");
        return "yuding/yudinglist.jsp";
    }
    
    @RequestMapping({ "/yudingupdate.do" })
    public String yudingupdate(final HttpServletRequest request, final int id) throws ParseException {
        final Yuding bean = this.yudingService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("yudingupdate2.do?id=" + id));
        request.setAttribute("title", (Object)"\u5904\u7406\u5ba2\u6237\u9884\u5b9a");
        return "yuding/yudingupdate.jsp";
    }
    
    @RequestMapping({ "/yudingupdate2.do" })
    public void yudingupdate2(final HttpServletResponse response, final HttpServletRequest request) {
        final String id = request.getParameter("id");
        final String zhuangtai = request.getParameter("zhuangtai");
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        final Yuding bean = this.yudingService.selectBeanById(Integer.parseInt(id));
        if ("\u9884\u5b9a\u6210\u529f".equals(zhuangtai)) {
            bean.setZhuangtai("\u9884\u5b9a\u6210\u529f");
            bean.setShijian2(Util.getTime());
            bean.setWorkid(user.getId());
            this.yudingService.updateBean(bean);
            final Car car = this.carService.selectBeanById(bean.getCarid());
            car.setStatus("\u79df\u8d41\u4e2d");
            this.carService.updateBean(car);
            final Jilu jilu = new Jilu();
            jilu.setBeizhu(bean.getBeizhu());
            jilu.setCarid(bean.getCarid());
            jilu.setChepai(bean.getChepai());
            jilu.setDianhua(bean.getDianhua());
            jilu.setJsz(bean.getJsz());
            jilu.setKehuid(bean.getKehuid());
            jilu.setSfz(bean.getSfz());
            jilu.setSfz2(bean.getSfz2());
            jilu.setShijian1(Util.getTime());
            jilu.setWorkid(user.getId());
            jilu.setXingming(bean.getXingming());
            jilu.setZhuangtai("\u51fa\u79df\u4e2d");
            this.jiluService.insertBean(jilu);
        }
        else {
            bean.setZhuangtai("\u9884\u5b9a\u5931\u8d25");
            bean.setShijian2(Util.getTime());
            bean.setWorkid(user.getId());
            this.yudingService.updateBean(bean);
            final Car car = this.carService.selectBeanById(bean.getCarid());
            car.setStatus("\u5f85\u79df\u8d41");
            this.carService.updateBean(car);
        }
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='yudinglist.do';</script>");
    }
    
    @RequestMapping({ "/yudinglist2.do" })
    public String yudinglist2(final HttpServletRequest request, final String pagenum, final String chepai, final String sfz) {
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
        if (sfz != null && !"".equals(sfz)) {
            request.setAttribute("sfz", (Object)sfz);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Yuding> list = this.yudingService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, sfz, 0, user.getId(), null);
        request.setAttribute("list", (Object)list);
        final int total = this.yudingService.selectBeanCount(chepai, sfz, 0, user.getId(), null);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "yudinglist2.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"yudinglist2.do");
        request.setAttribute("url2", (Object)"yuding");
        request.setAttribute("title", (Object)"\u6211\u5904\u7406\u7684\u9884\u5b9a");
        return "yuding/yudinglist2.jsp";
    }
    
    @RequestMapping({ "/yudingupdate3.do" })
    public String yudingupdate3(final HttpServletRequest request, final int id) throws ParseException {
        final Yuding bean = this.yudingService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u67e5\u770b\u9884\u5b9a\u8be6\u60c5");
        return "yuding/yudingupdate3.jsp";
    }
    
    @RequestMapping({ "/liuyanlist.do" })
    public String liuyanlist(final HttpServletRequest request, final String pagenum, final String ltitle) {
        if (ltitle != null && !"".equals(ltitle)) {
            request.setAttribute("ltitle", (Object)ltitle);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Liuyan> list = this.liuyanService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, ltitle, "\u672a\u56de\u590d", 0, 0);
        request.setAttribute("list", (Object)list);
        final int total = this.liuyanService.selectBeanCount(ltitle, "\u672a\u56de\u590d", 0, 0);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "liuyanlist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"liuyanlist.do");
        request.setAttribute("url2", (Object)"liuyan");
        request.setAttribute("title", (Object)"\u5f85\u56de\u590d\u7559\u8a00");
        return "liuyan/liuyanlist.jsp";
    }
    
    @RequestMapping({ "/liuyanupdate.do" })
    public String liuyanupdate(final HttpServletRequest request, final int id) throws ParseException {
        final Liuyan bean = this.liuyanService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("url", (Object)("liuyanupdate2.do?id=" + id));
        request.setAttribute("title", (Object)"\u56de\u590d\u7559\u8a00");
        return "liuyan/liuyanupdate.jsp";
    }
    
    @RequestMapping({ "/liuyanupdate2.do" })
    public void liuyanupdate2(final HttpServletResponse response, final HttpServletRequest request) {
        final String id = request.getParameter("id");
        final String huifu = request.getParameter("huifu");
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        final Liuyan bean = this.liuyanService.selectBeanById(Integer.parseInt(id));
        bean.setHuifu(huifu);
        bean.setShijian2(Util.getTime());
        bean.setZhuangtai("\u5df2\u56de\u590d");
        bean.setWorkid(user.getId());
        this.liuyanService.updateBean(bean);
        this.getPrintWriter(response).print("<script language=javascript>alert('\u64cd\u4f5c\u6210\u529f');window.location.href='liuyanlist.do';</script>");
    }
    
    @RequestMapping({ "/liuyanlist2.do" })
    public String liuyanlist2(final HttpServletRequest request, final String pagenum, final String ltitle) {
        final HttpSession session = request.getSession();
        final User user = (User)session.getAttribute("manage");
        if (ltitle != null && !"".equals(ltitle)) {
            request.setAttribute("ltitle", (Object)ltitle);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Liuyan> list = this.liuyanService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, ltitle, null, 0, user.getId());
        request.setAttribute("list", (Object)list);
        final int total = this.liuyanService.selectBeanCount(ltitle, null, 0, user.getId());
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "liuyanlist2.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"liuyanlist2.do");
        request.setAttribute("url2", (Object)"liuyan");
        request.setAttribute("title", (Object)"\u6211\u5904\u7406\u7684\u7559\u8a00");
        return "liuyan/liuyanlist2.jsp";
    }
    
    @RequestMapping({ "/liuyanupdate3.do" })
    public String liuyanupdate3(final HttpServletRequest request, final int id) throws ParseException {
        final Liuyan bean = this.liuyanService.selectBeanById(id);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u67e5\u770b\u7559\u8a00\u8be6\u60c5");
        return "liuyan/liuyanupdate3.jsp";
    }
    
    @RequestMapping({ "/userlist2.do" })
    public String userlist2(final HttpServletRequest request, final String pagenum, final String username) {
        if (username != null && !"".equals(username)) {
            request.setAttribute("username", (Object)username);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<User> list = this.userService.selectBeanList2((currentpage - 1) * this.pageSize, this.pageSize, username);
        request.setAttribute("list", (Object)list);
        final int total = this.userService.selectBeanCount2(username);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "userlist2.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"userlist2.do");
        request.setAttribute("url2", (Object)"user");
        request.setAttribute("title", (Object)"\u5ba2\u6237\u4fe1\u606f\u67e5\u8be2");
        return "user/userlist2.jsp";
    }
    
    @RequestMapping({ "/userlist3.do" })
    public String userlist3(final HttpServletRequest request, final String pagenum, final String username, final String vipstatus) {
        if (username != null && !"".equals(username)) {
            request.setAttribute("username", (Object)username);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Vip> list = this.userService.selectBeanList3((currentpage - 1) * this.pageSize, this.pageSize, username);
        request.setAttribute("list", (Object)list);
        final int total = this.userService.selectBeanCount3(username);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "userlist3.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"userlist3.do");
        request.setAttribute("url3", (Object)"user");
        request.setAttribute("title", (Object)"VIP\u5ba2\u6237\u4fe1\u606f\u67e5\u8be2");
        return "user/userlist3.jsp";
    }
    
    @RequestMapping({ "/userlist4.do" })
    public String userlist4(final HttpServletRequest request, final String pagenum, final String username) {
        if (username != null && !"".equals(username)) {
            request.setAttribute("username", (Object)username);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<User> list = this.userService.selectBeanList4((currentpage - 1) * this.pageSize, this.pageSize, username);
        request.setAttribute("list", (Object)list);
        final int total = this.userService.selectBeanCount4(username);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "userlist4.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"userlist4.do");
        request.setAttribute("url4", (Object)"user");
        request.setAttribute("title", (Object)"\u666e\u901a\u5ba2\u6237\u4fe1\u606f\u67e5\u8be2");
        return "user/userlist4.jsp";
    }
    
    @RequestMapping({ "/rilist.do" })
    public String rilist(final HttpServletRequest request, final String pagenum, final String ri) {
        final List<Ri> rilist = this.riService.selectBeanList(0, 9999, null);
        for (final Ri bean : rilist) {
            this.riService.deleteBean(bean.getId());
        }
        final List<Jilu> jilulist = this.jiluService.selectBeanList(0, 9999, null, null, null, "\u5b8c\u6210\u7ed3\u7b97", 0, 0, 0);
        for (final Jilu jilu : jilulist) {
            final String riqi = jilu.getShijian1().substring(0, 11);
            final List<Ri> rilist2 = this.riService.selectBeanList(0, 1, riqi);
            if (rilist2.size() <= 0) {
                final Ri bean2 = new Ri();
                bean2.setRi(riqi);
                bean2.setJine(jilu.getTzujin());
                bean2.setWeihu(jilu.getWeixiu());
                this.riService.insertBean(bean2);
            }
            else {
                final Ri bean2 = rilist2.get(0);
                bean2.setJine(bean2.getJine() + jilu.getTzujin());
                bean2.setWeihu(bean2.getWeihu() + jilu.getWeixiu());
                this.riService.updateBean(bean2);
            }
        }
        if (ri != null && !"".equals(ri)) {
            request.setAttribute("ri", (Object)ri);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Ri> list = this.riService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, ri);
        request.setAttribute("list", (Object)list);
        final int total = this.riService.selectBeanCount(ri);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "rilist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"rilist.do");
        request.setAttribute("url2", (Object)"ri");
        request.setAttribute("title", (Object)"\u65e5\u6536\u5165\u7edf\u8ba1");
        return "ri/rilist.jsp";
    }
    
    @RequestMapping({ "/yuelist.do" })
    public String yuelist(final HttpServletRequest request, final String pagenum, final String month) {
        final List<Yue> yuelist = this.yueService.selectBeanList(0, 9999, null);
        for (final Yue bean : yuelist) {
            this.yueService.deleteBean(bean.getId());
        }
        final List<Jilu> jilulist = this.jiluService.selectBeanList(0, 9999, null, null, null, "\u5b8c\u6210\u7ed3\u7b97", 0, 0, 0);
        for (final Jilu jilu : jilulist) {
            final String mon = jilu.getShijian1().substring(0, 7);
            final List<Yue> yuelist2 = this.yueService.selectBeanList(0, 1, mon);
            if (yuelist2.size() <= 0) {
                final Yue bean2 = new Yue();
                bean2.setMonth(mon);
                bean2.setJine(jilu.getTzujin());
                bean2.setWeihu(jilu.getWeixiu());
                this.yueService.insertBean(bean2);
            }
            else {
                final Yue bean2 = yuelist2.get(0);
                bean2.setJine(bean2.getJine() + jilu.getTzujin());
                bean2.setWeihu(bean2.getWeihu() + jilu.getWeixiu());
                this.yueService.updateBean(bean2);
            }
        }
        if (month != null && !"".equals(month)) {
            request.setAttribute("month", (Object)month);
        }
        int currentpage = 1;
        if (pagenum != null) {
            currentpage = Integer.parseInt(pagenum);
        }
        final List<Yue> list = this.yueService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, month);
        request.setAttribute("list", (Object)list);
        final int total = this.yueService.selectBeanCount(month);
        request.setAttribute("pageyuenfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "yuelist.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"yuelist.do");
        request.setAttribute("url2", (Object)"yue");
        request.setAttribute("title", (Object)"\u65e5\u6536\u5165\u7edf\u8ba1");
        return "yue/yuelist.jsp";
    }
    
    @RequestMapping({ "/carlist4.do" })
    public String carlist4(final HttpServletRequest request, final String pagenum, final String chepai, final String pinpai, final String xinghao, final String yanse) {
        if (chepai != null && !"".equals(chepai)) {
            request.setAttribute("chepai", (Object)chepai);
        }
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
        final List<Car> list = this.carService.selectBeanList((currentpage - 1) * this.pageSize, this.pageSize, chepai, pinpai, xinghao, yanse);
        request.setAttribute("list", (Object)list);
        final int total = this.carService.selectBeanCount(chepai, pinpai, xinghao, yanse);
        request.setAttribute("pagerinfo", (Object)Pager.getPagerNormal(total, this.pageSize, currentpage, "carlist4.do", "\u5171\u6709" + total + "\u6761\u8bb0\u5f55"));
        request.setAttribute("url", (Object)"carlist4.do");
        request.setAttribute("url2", (Object)"car");
        request.setAttribute("title", (Object)"\u8f66\u8f86\u6536\u5165\u7edf\u8ba1");
        return "car/carlist4.jsp";
    }
    
    @RequestMapping({ "/vipcharge.do" })
    public String vipcharge(final HttpServletRequest request, final String username) throws ParseException {
        final Vip bean = this.vipService.selectBeanByUsername(username);
        if (bean == null) {
            final Vip bean2 = new Vip();
            bean2.setUsername(username);
            final User user = this.userService.selectBeanByUsername(username);
            if (user != null) {
                bean2.setKehuid(user.getId());
            }
            bean2.setLevel(0);
            bean2.setPoints(0);
            request.setAttribute("bean", (Object)bean2);
            request.setAttribute("title", (Object)"\u4f1a\u5458\u5145\u503c/\u7eed\u8d39");
            return "vip/vipcharge.jsp";
        }
        final String enddate = bean.getEnddate().split(" ")[0];
        bean.setEnddate(enddate);
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u4f1a\u5458\u5145\u503c/\u7eed\u8d39");
        return "vip/vipcharge.jsp";
    }
    
    @RequestMapping({ "/vipcharge2.do" })
    public void vipcharge2(final HttpServletRequest request, final HttpServletResponse response, final String username, final int month, int points, final int kehuid) throws ParseException {
        final PrintWriter writer = this.getPrintWriter(response);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar rightNow = Calendar.getInstance();
        final String now = sdf.format(new Date());
        points += month * 1000;
        Double consumed = 0.0;
        switch (month) {
            case 12: {
                consumed += 20 * month * 0.9;
                break;
            }
            case 6: {
                consumed += 20 * month * 0.95;
                break;
            }
            default: {
                consumed += (Double)(20 * month);
                break;
            }
        }
        final Vip bean = this.vipService.selectBeanByUsername(username);
        if (bean != null) {
            bean.setPoints(points);
            consumed += bean.getConsumed();
            final int level = 1 + (int)Math.round(consumed) / 1000;
            bean.setConsumed(consumed);
            bean.setLevel(level);
            final String enddate = bean.getEnddate().split(" ")[0];
            if (enddate.compareTo(now) > 0) {
                final Date ed = sdf.parse(enddate);
                rightNow.setTime(ed);
                rightNow.add(2, month);
                final String newEndDate = sdf.format(rightNow.getTime());
                bean.setEnddate(newEndDate);
            }
            else {
                final Calendar nowCal = Calendar.getInstance();
                nowCal.add(2, month);
                final String newEndDate = sdf.format(nowCal.getTime());
                bean.setEnddate(newEndDate);
            }
            this.vipService.updateBean(bean);
        }
        else {
            final Vip bean2 = new Vip();
            bean2.setUsername(username);
            bean2.setKehuid(kehuid);
            bean2.setPoints(points);
            bean2.setConsumed(consumed);
            final int level2 = 1 + (int)Math.round(consumed) / 1000;
            bean2.setLevel(level2);
            final Calendar nowCal = Calendar.getInstance();
            nowCal.add(2, month);
            final String newEndDate = sdf.format(nowCal.getTime());
            bean2.setEnddate(newEndDate);
            this.vipService.insertBean(bean2);
        }
        request.setAttribute("bean", (Object)bean);
        request.setAttribute("title", (Object)"\u4f1a\u5458\u5145\u503c/\u7eed\u8d39\u9875");
        writer.print("<script language=javascript>alert('\u5145\u503c\u6210\u529f');window.location.href='vipcharge.do?username=" + username + "';</script>");
    }
}
