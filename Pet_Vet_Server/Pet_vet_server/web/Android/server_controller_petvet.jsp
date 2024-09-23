<%-- 
    Document   : server_controller_petvet
    Created on : 8 Jan, 2024, 5:22:46 PM
    Author     : Admin
--%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Iterator"%>
<%@page import="Connection.dbconnection"%>
<%
  dbconnection db = new dbconnection();
   String key = request.getParameter("requestType").trim();
   System.out.print(key);
   



//CLINIC REGISTER

 if (key.equals("clinic_reg")) {
        String un=request.getParameter("name");
         String l = request.getParameter("lic"); 
         String t = request.getParameter("type");
         String a = request.getParameter("address");
        String e = request.getParameter("email");
        String ph = request.getParameter("phone");
        String pas = request.getParameter("passwd");
        String w = request.getParameter("work");
         String se = request.getParameter("service");
        String d = request.getParameter("des");
        String i = request.getParameter("image");
          String insertQry = "SELECT COUNT(*) FROM center_reg WHERE `email`='"+e+"' OR `phone`='"+ph+"'";
        System.out.println(insertQry);
         Iterator it = db.getData(insertQry).iterator();
        System.out.println("heloooooooooooooooooo");
        if (it.hasNext()) {
            Vector vec = (Vector)it.next();
            int max_vid = Integer.parseInt(vec.get(0).toString());
            System.out.println(max_vid);        
             if (max_vid == 0) {
                String sq = "INSERT into center_reg(c_nam,type,lic,address,email,pass,phone,work_hour,service,descri,photo)values('"+un+"','"+t+"','"+l+"','" + a + "','" + e + "','" + pas + "','" + ph + "','" + w + "','" + se + "','" + d + "','" + i + "')";
                 String sq1 = "INSERT into login(u_id,unam,pass,type,status)values((select max(cl_id) from center_reg),'" + e + "','" + pas + "','"+t+"','0')";
                System.out.println(sq + "\n" + sq1);

              if (db.putData(sq) > 0 && db.putData(sq1) > 0) {
                // Get the generated u_id
                String getUIdQuery = "SELECT u_id FROM login WHERE unam='" + e + "' AND pass='" + pas + "'";
                Iterator uIdIterator = db.getData(getUIdQuery).iterator();

                if (uIdIterator.hasNext()) {
                    Vector uIdVec = (Vector) uIdIterator.next();
                    String uId = uIdVec.get(0).toString();
                    System.out.println("Uid"+uId);
                    System.out.println("Registered with u_id: " + uId);
                    out.println(uId); // Send u_id as a response
                } else {
                    System.out.println("Failed to retrieve u_id");
                    out.println("failed");
                }
            } else {
                System.out.println("Registration failed");
                out.println("failed");
            }
        } else {
            System.out.println("Already Exist");
            out.println("Already Exist");
        }
    } else {
        out.println("failed");
    }
}
 
 //CLINIC ADD DOCTOR

if (key.equals("vet_add_doc")) {
        String id = request.getParameter("vid");
        String na = request.getParameter("name");
         String a = request.getParameter("address");
        String p = request.getParameter("phone");
        String e = request.getParameter("email");
         String l = request.getParameter("License");
         String d = request.getParameter("des");
         String ex = request.getParameter("exper");
          String i = request.getParameter("image");
         String sql="INSERT INTO `doctors`(`v_id`,`dName`,`dAdress`,`dPhone`,`dEmail`,`dLice`,`dDesc`,`dExpe`,`dPic`)VALUES('"+id+"','"+na+"','"+a+"','"+p+"','"+e+"','"+l+"','"+d+"','"+ex+"','"+i+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


//CLINIC VIEW CONSULTATION REQUESTS

if (key.equals("clinic_view_req")) {
     String id = request.getParameter("cid");
        String str3 = "SELECT `consultations`.*,`petparent_reg`.`ow_nam` FROM `consultations`,`petparent_reg` WHERE `consultations`.`status`='requested' AND `consultations`.`c_id`='"+id+"' AND `consultations`.`u_id`=`petparent_reg`.`ow_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("consu_id", v.get(0).toString());
                jsonobj.put("cons_uid", v.get(1).toString());
                jsonobj.put("cons_cid", v.get(2).toString());
                jsonobj.put("cons_did", v.get(3).toString());
                 jsonobj.put("cons_dnam", v.get(4).toString());
                jsonobj.put("cons_issue", v.get(5).toString());
                 jsonobj.put("cons_des", v.get(6).toString());
                jsonobj.put("cons_date", v.get(7).toString());
                 jsonobj.put("cons_pic", v.get(8).toString());
                jsonobj.put("ow_nam", v.get(10).toString());
                
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }
 

//CLINIC SCHEDULE APPOINTMENTS

if (key.equals("vet_schedule_appointment")) {
        String cid = request.getParameter("cid");
        String did = request.getParameter("did");
        String id = request.getParameter("uid");
         String clid = request.getParameter("clid");
        String n = request.getParameter("d_nam");
        String ii = request.getParameter("issue");
         String  t= request.getParameter("time");
         String da = request.getParameter("date");
          String o = request.getParameter("o_nam");
         String sql="INSERT INTO `scheduled_appointments`(`co_id`,`did`,`cl_id`,`uid`,`dnam`,`p_nam`,`issue`,`date`,`time`)VALUES('"+cid+"','"+did+"','"+clid+"','"+id+"','"+n+"','"+o+"','"+ii+"','"+da+"','"+t+"')";
          String sqll = "UPDATE consultations SET status='scheduled' WHERE consu_id='"+cid+"' AND u_id='"+id+"'";
        int res2 = db.putData(sql);
        int res3 = db.putData(sqll);
        if (res2 > 0 && res3 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


//PET NURSERY VIEW ADMISSIONS
 
if (key.equals("pet_nursery_view_admissions")) {
    String id=request.getParameter("uid");
        String str3 = "SELECT `nursery_request`.*, `petparent_reg`.`ow_nam`,`petparent_reg`.`gender`,`petparent_reg`.`phone`,`petparent_reg`.`pet_pic` FROM `nursery_request`, `petparent_reg` WHERE `nursery_request`.`c_id`='"+id+"' AND `nursery_request`.`uid`=`petparent_reg`.`ow_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("n_reid", v.get(0).toString());
                jsonobj.put("n_cid", v.get(1).toString());
                jsonobj.put("n_cnam", v.get(2).toString());
                jsonobj.put("n_uid", v.get(3).toString());
                 jsonobj.put("n_date", v.get(4).toString());
                jsonobj.put("n_food", v.get(5).toString());
                jsonobj.put("n_water", v.get(6).toString());
                jsonobj.put("n_des", v.get(7).toString());
                 jsonobj.put("ow_nam", v.get(8).toString());
                jsonobj.put("pet_gen", v.get(9).toString());
                 jsonobj.put("o_phone", v.get(10).toString());
                 jsonobj.put("pet_pic", v.get(11).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


 //PET NURSERY ADD PHOTO

if (key.equals("nursery_pic")) {
        String id = request.getParameter("nur_id");
        String na = request.getParameter("rid");
         String a = request.getParameter("oid");
        String p = request.getParameter("oname");
          String i = request.getParameter("image");
         String sql="INSERT INTO `nursery_photos`(`n_id`,`re_id`,`uid`,`oname`,`pic`)VALUES('"+id+"','"+na+"','"+a+"','"+p+"','"+i+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }



//PET PARENT REGISTER

 if (key.equals("owner_reg")) {
        String un=request.getParameter("name");
        String a = request.getParameter("address");
        String e = request.getParameter("email");
        String ph = request.getParameter("phone");
        String pas = request.getParameter("passwd");
         String pn = request.getParameter("petname"); 
         String t = request.getParameter("type");
        String b = request.getParameter("breed");
         String g = request.getParameter("gender");
         String dob = request.getParameter("dob");
        String d = request.getParameter("des");
        String i = request.getParameter("image");
          String insertQry = "SELECT COUNT(*) FROM petparent_reg WHERE `email`='"+e+"' OR `phone`='"+ph+"'";
        System.out.println(insertQry);
         Iterator it = db.getData(insertQry).iterator();
        System.out.println("heloooooooooooooooooo");
        if (it.hasNext()) {
            Vector vec = (Vector)it.next();
            int max_vid = Integer.parseInt(vec.get(0).toString());
            System.out.println(max_vid);        
             if (max_vid == 0) {
                String sq = "INSERT into petparent_reg(ow_nam,own_add,phone,email,pass,pet_name,type,breed,gender,dob,des,pet_pic)values('"+un+"','"+a+"','"+ph+"','" + e + "','" + pas + "','" + pn + "','" + t + "','" + b + "','" + g + "','" + dob + "','" + d + "','" + i + "')";
                 String sq1 = "INSERT into login(u_id,unam,pass,type,status)values((select max(ow_id) from petparent_reg),'" + e + "','" + pas + "','petparent','1')";
                System.out.println(sq + "\n" + sq1);

              if (db.putData(sq) > 0 && db.putData(sq1) > 0) {
                // Get the generated u_id
                String getUIdQuery = "SELECT u_id FROM login WHERE unam='" + e + "' AND pass='" + pas + "'";
                Iterator uIdIterator = db.getData(getUIdQuery).iterator();

                if (uIdIterator.hasNext()) {
                    Vector uIdVec = (Vector) uIdIterator.next();
                    String uId = uIdVec.get(0).toString();
                    System.out.println("Uid"+uId);
                    System.out.println("Registered with u_id: " + uId);
                    out.println(uId); // Send u_id as a response
                } else {
                    System.out.println("Failed to retrieve u_id");
                    out.println("failed");
                }
            } else {
                System.out.println("Registration failed");
                out.println("failed");
            }
        } else {
            System.out.println("Already Exist");
            out.println("Already Exist");
        }
    } else {
        out.println("failed");
    }
}
 
 //PET PARENT VIEW CLINICS
 
if (key.equals("owners_view_clinic")) {
        String str3 = "SELECT `center_reg`.*,`login`.`status` FROM `center_reg`,`login` WHERE `login`.`status`='1' AND `login`.`type`='Veterinary Clinic' AND `center_reg`.`cl_id`=`login`.`u_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("cen_id", v.get(0).toString());
                jsonobj.put("cen_nam", v.get(1).toString());
                jsonobj.put("cen_typ", v.get(2).toString());
                jsonobj.put("cen_lic", v.get(3).toString());
                 jsonobj.put("cen_add", v.get(4).toString());
                jsonobj.put("cen_em", v.get(5).toString());
                jsonobj.put("cen_pass", v.get(6).toString());
                jsonobj.put("cen_ph", v.get(7).toString());
                 jsonobj.put("cen_wrk", v.get(8).toString());
                jsonobj.put("cen_serv", v.get(9).toString());
                jsonobj.put("cen_des", v.get(10).toString());
                jsonobj.put("cen_pic", v.get(11).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//PET PARENT VIEW DOCTORS
 
if (key.equals("owners_view_doc")) {
    String id=request.getParameter("clinic_id");
        String str3 = "SELECT *FROM `doctors` WHERE `v_id`='"+id+"'";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("do_id", v.get(0).toString());
                jsonobj.put("do_cl_id", v.get(1).toString());
                jsonobj.put("dnam", v.get(2).toString());
                jsonobj.put("daddr", v.get(3).toString());
                 jsonobj.put("dph", v.get(4).toString());
                jsonobj.put("d_em", v.get(5).toString());
                jsonobj.put("d_lic", v.get(6).toString());
                jsonobj.put("d_des", v.get(7).toString());
                 jsonobj.put("d_ex", v.get(8).toString());
                jsonobj.put("d_pic", v.get(9).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }
 
 //PET PARENT CONSULT DOCTOR

if (key.equals("consult_doc")) {
        String cid = request.getParameter("cid");
        String did = request.getParameter("did");
         String id = request.getParameter("uid");
        String n = request.getParameter("d_nam");
        String ii = request.getParameter("issue");
         String  d= request.getParameter("des");
         String da = request.getParameter("date");
          String i = request.getParameter("image");
         String sql="INSERT INTO `consultations`(`u_id`,`c_id`,`did`,`dnam`,`issue`,`des`,`date`,`pic`,`status`)VALUES('"+id+"','"+cid+"','"+did+"','"+n+"','"+ii+"','"+d+"','"+da+"','"+i+"','requested')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }



//PET PARENT VIEW APPOINTMENTS
 
if (key.equals("owners_view_appointment")) {
    String id=request.getParameter("clinic_id");
    String uid=request.getParameter("own_id");
        String str3 = "SELECT `scheduled_appointments`.*, `center_reg`.`c_nam` FROM `scheduled_appointments`, `center_reg` WHERE `scheduled_appointments`.`cl_id`='"+id+"' AND `scheduled_appointments`.`uid`='"+uid+"' AND `scheduled_appointments`.`cl_id`=`center_reg`.`cl_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("ap_id", v.get(0).toString());
                jsonobj.put("ap_co_id", v.get(1).toString());
                jsonobj.put("ap_did", v.get(2).toString());
                jsonobj.put("ap_clid", v.get(3).toString());
                 jsonobj.put("ap_uid", v.get(4).toString());
                jsonobj.put("ap_dnam", v.get(5).toString());
                jsonobj.put("ap_pnam", v.get(6).toString());
                jsonobj.put("ap_issue", v.get(7).toString());
                 jsonobj.put("ap_date", v.get(8).toString());
                jsonobj.put("ap_time", v.get(9).toString());
                 jsonobj.put("cen_nam", v.get(10).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//PET PARENT SENT FEEDBACK

if (key.equals("owner_send_feedback")) {
        String cid = request.getParameter("clinic_id");
        String uid = request.getParameter("uid");
         String na = request.getParameter("cen_nam");
        String s = request.getParameter("subject");
        String d = request.getParameter("descr");
         String  r= request.getParameter("rating");
         String sql="INSERT INTO `feedback`(`cl_id`,`uid`,`cen_nam`,`sub`,`des`,`rating`)VALUES('"+cid+"','"+uid+"','"+na+"','"+s+"','"+d+"','"+r+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }




//PET PARENT VIEW PET SHOPS
 
if (key.equals("owners_view_shop")) {
        String str3 = "SELECT `center_reg`.*,`login`.`status` FROM `center_reg`,`login` WHERE `login`.`status`='1' AND `login`.`type`='Pet Shop' AND `center_reg`.`cl_id`=`login`.`u_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("cen_id", v.get(0).toString());
                jsonobj.put("cen_nam", v.get(1).toString());
                jsonobj.put("cen_typ", v.get(2).toString());
                jsonobj.put("cen_lic", v.get(3).toString());
                 jsonobj.put("cen_add", v.get(4).toString());
                jsonobj.put("cen_em", v.get(5).toString());
                jsonobj.put("cen_pass", v.get(6).toString());
                jsonobj.put("cen_ph", v.get(7).toString());
                 jsonobj.put("cen_wrk", v.get(8).toString());
                jsonobj.put("cen_serv", v.get(9).toString());
                jsonobj.put("cen_des", v.get(10).toString());
                jsonobj.put("cen_pic", v.get(11).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//PET PARENT VIEW PET PRODUCTS
 
if (key.equals("pet_parent_view_product")) {
     String id = request.getParameter("shop_id");
        String str3 = "SELECT *FROM `petshop_products` WHERE shop_id='"+id+"'";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("pdt_id", v.get(0).toString());
                jsonobj.put("pdt_shop_id", v.get(1).toString());
                jsonobj.put("pdt_name", v.get(2).toString());
                jsonobj.put("pdt_des", v.get(3).toString());
                 jsonobj.put("pdt_cate", v.get(4).toString());
                jsonobj.put("pdt_price", v.get(5).toString());
                jsonobj.put("pdt_pic", v.get(6).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//PET SHOP BUY PRODUCTS

if (key.equals("pet_parent_buy_product")) {
        String bid = request.getParameter("own_id");
        String pid = request.getParameter("pdt_id");
         String sid = request.getParameter("shop_id");
        String n = request.getParameter("pdt_nam");
        String p = request.getParameter("price");
         String  q= request.getParameter("quantity");
         String t = request.getParameter("total");
        String cv = request.getParameter("cvv");
         String cn = request.getParameter("cardno");
        String a = request.getParameter("acc_num");
        String an = request.getParameter("acc_nam");
         String  e= request.getParameter("exp_date");
         String sql="INSERT INTO `product_order`(`pdt_id`,`shop_id`,`buyr_id`,`pdt_nam`,`price`,`quantity`,`total`,`acc_nam`,`acc_num`,`cvv`,`card_no`,`exp_date`)VALUES('"+pid+"','"+sid+"','"+bid+"','"+n+"','"+p+"','"+q+"','"+t+"','"+an+"','"+a+"','"+cv+"','"+cn+"','"+e+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


//PET PARENT VIEW ADOPTIONS
 
if (key.equals("owners_view_adoptions")) {
     String id = request.getParameter("shop_id");
        String str3 = "SELECT *FROM `adoption` WHERE shop_id='"+id+"'";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("adop_id", v.get(0).toString());
                jsonobj.put("adop_shop_id", v.get(1).toString());
                jsonobj.put("adop_typ", v.get(2).toString());
                jsonobj.put("adop_gen", v.get(3).toString());
                 jsonobj.put("adop_bre", v.get(4).toString());
                jsonobj.put("adop_size", v.get(5).toString());
                jsonobj.put("adop_heal", v.get(6).toString());
                jsonobj.put("adop_age", v.get(7).toString());
                jsonobj.put("adop_fees", v.get(8).toString());
                jsonobj.put("adop_pic", v.get(9).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//PET PARENT BUY PRODUCTS

if (key.equals("pet_parent_req_adoption")) {
        String bid = request.getParameter("own_id");
        String pid = request.getParameter("adopt_id");
         String sid = request.getParameter("shop_id");
        String n = request.getParameter("type");
        String p = request.getParameter("breed");
         String  q= request.getParameter("reason");
         String t = request.getParameter("total");
        String cv = request.getParameter("cvv");
         String cn = request.getParameter("cardno");
        String a = request.getParameter("acc_num");
        String an = request.getParameter("acc_nam");
         String  e= request.getParameter("exp_date");
         String sql="INSERT INTO `adoption_requests`(`ad_id`,`shop_id`,`u_id`,`type`,`breed`,`reason`,`total`,`acc_nam`,`acc_num`,`cvv`,`card_no`,`exp_date`,`status`)VALUES('"+pid+"','"+sid+"','"+bid+"','"+n+"','"+p+"','"+q+"','"+t+"','"+an+"','"+a+"','"+cv+"','"+cn+"','"+e+"','requested')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }



 //PET PARENT VIEW PET NURSERY
 
if (key.equals("owners_view_nursery")) {
        String str3 = "SELECT `center_reg`.*,`login`.`status` FROM `center_reg`,`login` WHERE `login`.`status`='1' AND `login`.`type`='Pet Nursery' AND `center_reg`.`cl_id`=`login`.`u_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("cen_id", v.get(0).toString());
                jsonobj.put("cen_nam", v.get(1).toString());
                jsonobj.put("cen_typ", v.get(2).toString());
                jsonobj.put("cen_lic", v.get(3).toString());
                 jsonobj.put("cen_add", v.get(4).toString());
                jsonobj.put("cen_em", v.get(5).toString());
                jsonobj.put("cen_pass", v.get(6).toString());
                jsonobj.put("cen_ph", v.get(7).toString());
                 jsonobj.put("cen_wrk", v.get(8).toString());
                jsonobj.put("cen_serv", v.get(9).toString());
                jsonobj.put("cen_des", v.get(10).toString());
                jsonobj.put("cen_pic", v.get(11).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//PET PARENT REQUEST NURSERY

if (key.equals("owner_request_nursery")) {
        String bid = request.getParameter("cid");
        String pid = request.getParameter("uid");
         String sid = request.getParameter("c_nam");
        String n = request.getParameter("food");
        String p = request.getParameter("water");
         String  q= request.getParameter("date");
         String t = request.getParameter("des");
         String sql="INSERT INTO `nursery_request`(`c_id`,`cnam`,`uid`,`date`,`food`,`water`,`des`)VALUES('"+bid+"','"+sid+"','"+pid+"','"+q+"','"+n+"','"+p+"','"+t+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }



//PET PARENT VIEW PHOTO 
 
if (key.equals("pet_parent_view_pic")) {
     String id = request.getParameter("nur_id");
        String str3 = "SELECT `nursery_photos`.*, `petparent_reg`.`ow_id`,`center_reg`.`c_nam` FROM `nursery_photos`,`petparent_reg`,`center_reg` WHERE `nursery_photos`.`uid`=`petparent_reg`.`ow_id` AND `nursery_photos`.`n_id`='"+id+"' AND `center_reg`.`cl_id`=`nursery_photos`.`n_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("np_pid", v.get(0).toString());
                jsonobj.put("np_nid", v.get(1).toString());
                jsonobj.put("np_rid", v.get(2).toString());
                jsonobj.put("np_uid", v.get(3).toString());
                 jsonobj.put("np_onam", v.get(4).toString());
                jsonobj.put("np_pic", v.get(5).toString());
                jsonobj.put("ow_id", v.get(6).toString());
                jsonobj.put("cen_nam", v.get(7).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//PAT PARENT VIEW DOG WALKERS

if (key.equals("petparent_view_walker")) {
        String str3 = "SELECT `dog_walker_reg`.*,`login`.`status` FROM `dog_walker_reg`,`login` WHERE `login`.`status`='1' AND `login`.`type`='dogwalker' AND `dog_walker_reg`.`wa_id`=`login`.`u_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("wa_id", v.get(0).toString());
                jsonobj.put("wa_nam", v.get(1).toString());
                jsonobj.put("wa_add", v.get(2).toString());
                jsonobj.put("wa_ph", v.get(3).toString());
                 jsonobj.put("wa_em", v.get(4).toString());
                jsonobj.put("wa_pas", v.get(5).toString());
                jsonobj.put("wa_lic", v.get(6).toString());
                jsonobj.put("wa_sp", v.get(7).toString());
                 jsonobj.put("wa_ex", v.get(8).toString());
                jsonobj.put("wa_pic", v.get(9).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//PET PARENT SEARCH DOG WALKER

if (key.equals("getSearchResult")) {
        System.out.println("heloo");
        String searchText = request.getParameter("searchValue").trim();
        String text = "%" + searchText + "%";
        String qry = "select * from `dog_walker_reg` where address like '" + text + "'";
        System.out.println("qry=" + qry);
        JSONArray array = new JSONArray();
        Iterator itr = db.getData(qry).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                 jsonobj.put("wa_id", v.get(0).toString());
                jsonobj.put("wa_nam", v.get(1).toString());
                jsonobj.put("wa_add", v.get(2).toString());
                jsonobj.put("wa_ph", v.get(3).toString());
                 jsonobj.put("wa_em", v.get(4).toString());
                jsonobj.put("wa_pas", v.get(5).toString());
                jsonobj.put("wa_lic", v.get(6).toString());
                jsonobj.put("wa_sp", v.get(7).toString());
                 jsonobj.put("wa_ex", v.get(8).toString());
                jsonobj.put("wa_pic", v.get(9).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);
            
            
        } else {
            out.println("failed");
        }
    }



//PET PARENT REQUEST DOG WALKER

if (key.equals("req_dog_walker")) {
        String did = request.getParameter("do_id");
        String uid = request.getParameter("u_id");
         String n = request.getParameter("d_name");
        String d = request.getParameter("date");
        String t = request.getParameter("time");
         String sql="INSERT INTO `dog_walker_request`(`do_id`,`u_id`,`d_nam`,`date`,`time`,`status`)VALUES('"+did+"','"+uid+"','"+n+"','"+d+"','"+t+"','requested')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }





//DOG WALKER REGISTER

 if (key.equals("dog_walker_reg")) {
        String un=request.getParameter("name");
         String l = request.getParameter("License"); 
         String a = request.getParameter("address");
        String e = request.getParameter("email");
        String ph = request.getParameter("phone");
        String pas = request.getParameter("passwd");
        String s = request.getParameter("special");
         String ex = request.getParameter("exper");
        String i = request.getParameter("image");
          String insertQry = "SELECT COUNT(*) FROM dog_walker_reg WHERE `email`='"+e+"' OR `phone`='"+ph+"'";
        System.out.println(insertQry);
         Iterator it = db.getData(insertQry).iterator();
        System.out.println("heloooooooooooooooooo");
        if (it.hasNext()) {
            Vector vec = (Vector)it.next();
            int max_vid = Integer.parseInt(vec.get(0).toString());
            System.out.println(max_vid);        
             if (max_vid == 0) {
                String sq = "INSERT into dog_walker_reg(name,address,phone,email,pass,lic,special,exper,photo)values('"+un+"','"+a+"','"+ph+"','" + e + "','" + pas + "','" + l + "','" + s + "','" + ex + "','" + i + "')";
                 String sq1 = "INSERT into login(u_id,unam,pass,type,status)values((select max(wa_id) from dog_walker_reg),'" + e + "','" + pas + "','dogwalker','0')";
                System.out.println(sq + "\n" + sq1);

              if (db.putData(sq) > 0 && db.putData(sq1) > 0) {
                // Get the generated u_id
                String getUIdQuery = "SELECT u_id FROM login WHERE unam='" + e + "' AND pass='" + pas + "'";
                Iterator uIdIterator = db.getData(getUIdQuery).iterator();

                if (uIdIterator.hasNext()) {
                    Vector uIdVec = (Vector) uIdIterator.next();
                    String uId = uIdVec.get(0).toString();
                    System.out.println("Uid"+uId);
                    System.out.println("Registered with u_id: " + uId);
                    out.println(uId); // Send u_id as a response
                } else {
                    System.out.println("Failed to retrieve u_id");
                    out.println("failed");
                }
            } else {
                System.out.println("Registration failed");
                out.println("failed");
            }
        } else {
            System.out.println("Already Exist");
            out.println("Already Exist");
        }
    } else {
        out.println("failed");
    }
}
 

 
 //DOG WALKER VIEW REQUESTS
 
if (key.equals("dog_walker_view_requests")) {
     String id = request.getParameter("uid");
        String str3 = "SELECT `dog_walker_request`.*,`petparent_reg`.`ow_nam`, `petparent_reg`.`type`,`petparent_reg`.`pet_pic` FROM `dog_walker_request`, `petparent_reg` WHERE `dog_walker_request`.`do_id`='" +id+ "' AND `petparent_reg`.`ow_id`=`dog_walker_request`.`u_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("dw_re_id", v.get(0).toString());
                jsonobj.put("dw_did", v.get(1).toString());
                jsonobj.put("dw_uid", v.get(2).toString());
                jsonobj.put("dw_dnam", v.get(3).toString());
                 jsonobj.put("dw_date", v.get(4).toString());
                jsonobj.put("dw_time", v.get(5).toString());
                jsonobj.put("dw_status", v.get(6).toString());
                jsonobj.put("ow_nam", v.get(7).toString());
                jsonobj.put("pet_typ", v.get(8).toString());
                jsonobj.put("pet_pic", v.get(9).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



 
//LOGIN
if (key.equals("login")) {
        String ln = request.getParameter("uname");
        String lp = request.getParameter("pass");
        String st = "SELECT * from login where unam='" + ln + "' and pass='" + lp + "' AND status='1'";
        System.out.println(st);
        
        Iterator it = db.getData(st).iterator();
        if (it.hasNext()) {
            Vector v = (Vector) it.next();
            String ress = v.get(1) + "#" + v.get(4)+"#";
            out.print(ress);
            System.out.println("ss"+ress);
        } else {
            out.print("failed");
             System.out.println("No"+st);
        }
    }


//PET SHOP ADD PRODUCTS

if (key.equals("petshop_add_product")) {
        String pid = request.getParameter("pid");
        String n = request.getParameter("name");
         String p = request.getParameter("price");
        String d = request.getParameter("des");
        String c = request.getParameter("category");
         String  i= request.getParameter("image");
         String sql="INSERT INTO `petshop_products`(`shop_id`,`pdt_name`,`des`,`category`,`price`,`pic`)VALUES('"+pid+"','"+n+"','"+d+"','"+c+"','"+p+"','"+i+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


//PET SHOP ADD ADOPTION

if (key.equals("petshop_add_adoption")) {
        String pid = request.getParameter("pid");
        String t = request.getParameter("type");
         String g = request.getParameter("gender");
        String b = request.getParameter("breed");
        String s = request.getParameter("size");
        String h = request.getParameter("health");
        String a = request.getParameter("age");
        String f = request.getParameter("fees");
         String  i= request.getParameter("image");
         String sql="INSERT INTO `adoption`(`shop_id`,`type`,`gender`,`breed`,`size`,`health`,`age`,`fees`,`pic`)VALUES('"+pid+"','"+t+"','"+g+"','"+b+"','"+s+"','"+h+"','"+a+"','"+f+"','"+i+"')";
        int res2 = db.putData(sql);
        if (res2 > 0) {
            out.print("success");
        } else {
            out.print("failed");

        }
    }


//PET SHOP VIEW PRODUCT ORDERS
 
if (key.equals("pet_shop_view_orders")) {
     String id = request.getParameter("petshop_id");
        String str3 = "SELECT `product_order`.*, `petshop_products`.`pic` FROM `product_order`, `petshop_products` WHERE `product_order`.`shop_id`='"+id+"' AND `petshop_products`.`pdt_id`=`product_order`.`pdt_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("or_id", v.get(0).toString());
                jsonobj.put("or_pdt_id", v.get(1).toString());
                jsonobj.put("or_shop_id", v.get(2).toString());
                jsonobj.put("or_buyr_id", v.get(3).toString());
                 jsonobj.put("or_pdt_nam", v.get(4).toString());
                jsonobj.put("or_price", v.get(5).toString());
                jsonobj.put("or_quauntity", v.get(6).toString());
                jsonobj.put("or_total", v.get(7).toString());
                jsonobj.put("or_acc_num", v.get(9).toString());
                jsonobj.put("or_acc_nam", v.get(8).toString());
                  jsonobj.put("or_cvv", v.get(10).toString());
                jsonobj.put("or_card_num", v.get(11).toString());
                jsonobj.put("or_expdate", v.get(12).toString());
                jsonobj.put("pdt_pic", v.get(13).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }




//PET SHOP VIEW ADOPTION REQUESTS
 
if (key.equals("pet_shop_view_adoptions")) {
     String id = request.getParameter("petshop_id");
        String str3 = "SELECT `adoption_requests`.*,`adoption`.`pic` FROM `adoption_requests`, `adoption` WHERE `adoption_requests`.`shop_id`='"+id+"' AND `adoption`.`ad_id`=`adoption_requests`.`ad_id`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("ad_req_id", v.get(0).toString());
                jsonobj.put("ad_ad_id", v.get(1).toString());
                jsonobj.put("ad_reqshop_id", v.get(2).toString());
                 jsonobj.put("ad_requid", v.get(3).toString());
                jsonobj.put("ad_reqtyp", v.get(4).toString());
                jsonobj.put("ad_re_breed", v.get(5).toString());
                jsonobj.put("ad_re_reason", v.get(6).toString());
                jsonobj.put("ad_re_tot", v.get(7).toString());
                jsonobj.put("ad_re_accnum", v.get(9).toString());
                  jsonobj.put("ad_re_accnam", v.get(8).toString());
                jsonobj.put("ad_re_card_no", v.get(10).toString());
                jsonobj.put("ad_re_cvv", v.get(11).toString());
                jsonobj.put("ad_re_exp_date", v.get(12).toString());
                 jsonobj.put("ad_re_status", v.get(13).toString());
                jsonobj.put("adop_pic", v.get(14).toString());
                
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//ADMIN VIEW CENTERS
if (key.equals("admin_view_cen")) {
        String str3 = "SELECT *FROM `center_reg`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("cen_id", v.get(0).toString());
                jsonobj.put("cen_nam", v.get(1).toString());
                jsonobj.put("cen_typ", v.get(2).toString());
                jsonobj.put("cen_lic", v.get(3).toString());
                 jsonobj.put("cen_add", v.get(4).toString());
                jsonobj.put("cen_em", v.get(5).toString());
                jsonobj.put("cen_pass", v.get(6).toString());
                jsonobj.put("cen_ph", v.get(7).toString());
                 jsonobj.put("cen_wrk", v.get(8).toString());
                jsonobj.put("cen_serv", v.get(9).toString());
                jsonobj.put("cen_des", v.get(10).toString());
                jsonobj.put("cen_pic", v.get(11).toString());
       
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//ADMIN ACCEPT CENTER

if (key.equals("acceptcenter")) {
        String  id=request.getParameter("cid");
        String sqll = "UPDATE login SET status='1' WHERE u_id='"+id+"' AND (type='Veterinary Clinic' OR type='Pet Shop' OR type='Pet Nursery')";
                System.out.println(sqll);

        int resu = db.putData(sqll);
        if (resu > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }


//ADMIN VIEW DOG WALKERS

if (key.equals("admin_view_walker")) {
        String str3 = "SELECT *FROM `dog_walker_reg`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("wa_id", v.get(0).toString());
                jsonobj.put("wa_nam", v.get(1).toString());
                jsonobj.put("wa_add", v.get(2).toString());
                jsonobj.put("wa_ph", v.get(3).toString());
                 jsonobj.put("wa_em", v.get(4).toString());
                jsonobj.put("wa_pas", v.get(5).toString());
                jsonobj.put("wa_lic", v.get(6).toString());
                jsonobj.put("wa_sp", v.get(7).toString());
                 jsonobj.put("wa_ex", v.get(8).toString());
                jsonobj.put("wa_pic", v.get(9).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }


//ADMIN ACCEPT DOGWAKLERS

if (key.equals("acceptwalker")) {
        String  id=request.getParameter("wid");
        String sqll = "UPDATE login SET status='1' WHERE u_id='"+id+"' AND type='dogwalker'";
                System.out.println(sqll);

        int resu = db.putData(sqll);
        if (resu > 0) {
            out.print("success");
        } else {
            out.print("failed");
        }
    }


//ADMIN VIEW OWNERS
if (key.equals("admin_view_owners")) {
        String str3 = "SELECT *FROM `petparent_reg`";
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("ow_id", v.get(0).toString());
                jsonobj.put("ow_nam", v.get(1).toString());
                jsonobj.put("own_add", v.get(2).toString());
                jsonobj.put("o_phone", v.get(3).toString());
                 jsonobj.put("o_email", v.get(4).toString());
                jsonobj.put("pet_typ", v.get(7).toString());
                
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }



//ADMIN VIEW FEED BACK
if (key.equals("admin_view_feedback")) {
   
        String str3 = "SELECT *FROM `feedback`";
        System.out.println(str3);
        Iterator itr = db.getData(str3).iterator();
        JSONArray jsonarray = new JSONArray();
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Vector v = (Vector) itr.next();
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("fid", v.get(0).toString());
                jsonobj.put("f_clid", v.get(1).toString());
                jsonobj.put("f_uid", v.get(2).toString());
                jsonobj.put("f_cen_nam", v.get(3).toString());
                jsonobj.put("f_sub", v.get(4).toString());
                jsonobj.put("f_des", v.get(5).toString());
                jsonobj.put("f_rate", v.get(6).toString());
                jsonarray.add(jsonobj);   
            }
            out.print(jsonarray);

            System.out.print(jsonarray);

        } else {
            out.println("failed");
        }
    }

   %>