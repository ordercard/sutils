package com.spring.sutils.vaild;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
使用了Apache的poi   hssf  不过只能针对07之前的xls
对于新版的还是得使用xssf
@auther贾慧强
 */
@Component
public class ExcelUtil {

/*
参数为读取最大行数
 */
public static List<SysUser> praseXls(InputStream is, int maxRows) throws IOException {
    HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
    List<SysUser> nos = new ArrayList<>();
    // 循环工作表Sheet
    HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
//		int num = hssfSheet.getLastRowNum(); //值为记录数减1
    if(hssfSheet.getLastRowNum() > maxRows){
        throw new BaseException("超过最大导入数量！");
    }
    // 循环行Row
    HSSFRow h0 = hssfSheet.getRow(0);
    HSSFCell c0 = h0.getCell(0);
    String n1 = getValueFromHssf(c0);
    System.out.println(n1);
   if (!n1.equals("用户名")){
       throw new BaseException("表头1错误");
      }
    HSSFCell c1 = h0.getCell(1);
    String n2 = getValueFromHssf(c1);
    if (!n2.equals("姓名")){
        throw new BaseException("表头2错误");
    }
    HSSFCell c2 = h0.getCell(2);
    String n3 = getValueFromHssf(c2);
    if (!n3.equals("班级")){
        throw new BaseException("表头3错误");
    }
    HSSFCell c3 = h0.getCell(3);
    String n4 = getValueFromHssf(c3);
    if (!n4.equals("性别")){
        throw new BaseException("表头4错误");
    }
    HSSFCell c4 = h0.getCell(4);
    String n5 = getValueFromHssf(c4);
    if (!n5.equals("手机号")){
        throw new BaseException("表头5错误");
    }
    HSSFCell c5 = h0.getCell(5);
    String n6 = getValueFromHssf(c5);
    if (!n6.equals("邮箱")){
        throw new BaseException("表头6错误");
    }


    for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
        HSSFRow hssfRow = hssfSheet.getRow(rowNum);
        if (hssfRow == null) {
            continue;
        }
        // 获取首列
        SysUser sysUser =new SysUser();

        HSSFCell cell0 = hssfRow.getCell(0);
        if (cell0 == null) {
            continue;
        }else {
            String no = getValueFromHssf(cell0);
            if( no == null || "".equals(no)){
                continue;
            }else {
                sysUser.setUsername(no);
            }
        }
        HSSFCell cell1 = hssfRow.getCell(1);
        if (cell0 == null) {
            continue;
        }else {
            String no = getValueFromHssf(cell1);
            if( no == null || "".equals(no)){
                continue;
            }else {
                sysUser.setName(no);
            }
        } HSSFCell cell2= hssfRow.getCell(2);
        if (cell0 == null) {
            continue;
        }else {
            String no = getValueFromHssf(cell2);
            if( no == null || "".equals(no)){
                continue;
            }else {
                sysUser.setGrade(no);
            }
        }
        HSSFCell cell3= hssfRow.getCell(3);
        if (cell0 == null) {
            continue;
        }else {
            String no = getValueFromHssf(cell3);
            if( no == null || "".equals(no)){
                continue;
            }else {
                sysUser.setSex(no);
            }
        }
        HSSFCell cell4= hssfRow.getCell(4);
        if (cell0 == null) {
            continue;
        }else {
            String no = getValueFromHssf(cell4);
            if( no == null || "".equals(no)){
                continue;
            }else {
                sysUser.setPhone(no);
            }
        }
        HSSFCell cell5= hssfRow.getCell(5);
        if (cell0 == null) {
            continue;
        }else {
            String no = getValueFromHssf(cell5);
            if( no == null || "".equals(no)){
                continue;
            }else {
                sysUser.setEmail(no);
            }
        }
        sysUser.setOrganizationId(0l);
        sysUser.setCreateTime(new Date());
        sysUser.setLocked(false);
         String  p=  md5("123456");
        sysUser.setPassword("123456");
        sysUser.setRoleIdsStr("");
        sysUser.setSalt("");
       nos.add(sysUser);
    }
    return nos;
}
    private  static String md5(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            return base64en.encode(md5.digest(s.getBytes("utf-8")));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ServiceException("修改密码失败");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ServiceException("修改密码失败");
        }
    }

    /**
     * 得到Excel表中的值 (.xls文件)
     * @param hssfCell
     *            Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    public static String getValueFromHssf(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型对应的字符串(处理手机号变科学计数问题)
            DecimalFormat df = new DecimalFormat("0");
            String valueStr = df.format(hssfCell.getNumericCellValue());
            return valueStr;
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    public static void exportExcel(HttpServletRequest request, HttpServletResponse httpServletResponse ,List<SysUser> list) throws  IOException{
        httpServletResponse.setContentType("application/force-download");
        httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=" +"u1.xls");
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("用户信息导出");
        // 表头
        HSSFRow hssfRow0 = hssfSheet.createRow(0);
        HSSFCell hssfCell0 = hssfRow0.createCell(0);
        hssfCell0.setCellType(Cell.CELL_TYPE_STRING);
        hssfCell0.setCellValue("用户账号");
        HSSFCell hssfCell1 = hssfRow0.createCell(1);
        hssfCell1.setCellType(Cell.CELL_TYPE_STRING);
        hssfCell1.setCellValue("用户名");

        HSSFCell hssfCell2= hssfRow0.createCell(2);
        hssfCell2.setCellType(Cell.CELL_TYPE_STRING);
        hssfCell2.setCellValue("性别");
        HSSFCell hssfCell3= hssfRow0.createCell(3);
        hssfCell3.setCellType(Cell.CELL_TYPE_STRING);
        hssfCell3.setCellValue("班级");
        HSSFCell hssfCell4= hssfRow0.createCell(4);
        hssfCell4.setCellType(Cell.CELL_TYPE_STRING);
        hssfCell4.setCellValue("邮箱");
        HSSFCell hssfCell5= hssfRow0.createCell(5);
        hssfCell5.setCellType(Cell.CELL_TYPE_STRING);
        hssfCell5.setCellValue("手机号");


        for (int j=0;j<list.size();j++){
            HSSFRow hssfRow = hssfSheet.createRow(j+1);
                HSSFCell hssfCells = hssfRow.createCell(0);
            HSSFCell hssfCell00 = hssfRow.createCell(0);
            hssfCell00.setCellType(Cell.CELL_TYPE_STRING);
            hssfCell00.setCellValue(list.get(j).getUsername());
            HSSFCell hssfCell10 = hssfRow.createCell(1);
            hssfCell10.setCellType(Cell.CELL_TYPE_STRING);
            hssfCell10.setCellValue(list.get(j).getName());

            HSSFCell hssfCell20= hssfRow.createCell(2);
            hssfCell20.setCellType(Cell.CELL_TYPE_STRING);
            hssfCell20.setCellValue(list.get(j).getSex());
            HSSFCell hssfCell30= hssfRow.createCell(3);
            hssfCell30.setCellType(Cell.CELL_TYPE_STRING);
            hssfCell30.setCellValue(list.get(j).getGrade());
            HSSFCell hssfCell40= hssfRow.createCell(4);
            hssfCell40.setCellType(Cell.CELL_TYPE_STRING);
            hssfCell40.setCellValue(list.get(j).getEmail());
            HSSFCell hssfCell50= hssfRow.createCell(5);
            hssfCell50.setCellType(Cell.CELL_TYPE_STRING);
            hssfCell50.setCellValue(list.get(j).getPhone());
        }

        String realpath = request.getSession().getServletContext().getRealPath("/resources/temp");
        System.out.println(realpath);
        String fileName = UUID.randomUUID().toString()+".xls";
        File targetFile = new File(realpath, fileName);
        System.out.println(targetFile.getName());
        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
            targetFile.createNewFile();
            System.out.println(targetFile.getAbsolutePath());
        }

        FileOutputStream fileOutputStream= new FileOutputStream(targetFile);
        hssfWorkbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        OutputStream outputStream = new DataOutputStream(httpServletResponse.getOutputStream());
        DataInputStream in = new DataInputStream(new FileInputStream(targetFile));
        int bytes;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            outputStream.write(bufferOut, 0, bytes);
        }
        in.close();
        if (targetFile.exists()) {
            if (targetFile.delete()) {
                System.out.println("file {} is deleted!"+targetFile.getPath());
            }
        }

        outputStream.flush();
        outputStream.close();
    }

}
