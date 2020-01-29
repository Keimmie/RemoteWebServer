public class BackgroundTask extends AsyncTask<String, Void, String> {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;

    BackgroundTask(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        preferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("flag","0");
        editor.commit();

        String urlRegistration = "http://localhosturl/Folder/register.php";
        String urlLogin  = "http:///localhosturl/Folder/login.php";
        String urlRecord = "http://localhosturl/Folder/insert_data.php";

        String task = params[0];

        if(task.equals("register")){
            String empId = params[1];
            String firstName = params[2];
            String mInitial = params[3];
            String lastName = params[4];
            String dob = params[5];
            String height = params[6];
            String weight = params[7];

            try {
                URL url = new URL(urlRegistration);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("idEmployee","UTF-8")+"="+ URLEncoder.encode(empId,"UTF-8")+"&"
                        + URLEncoder.encode("FirstName","UTF-8")+"="+ URLEncoder.encode(firstName,"UTF-8")+"&"
                        + URLEncoder.encode("MiddleInitial","UTF-8")+"="+ URLEncoder.encode(mInitial,"UTF-8")+"&"
                        + URLEncoder.encode("LastName","UTF-8")+"="+ URLEncoder.encode(lastName,"UTF-8")+"&"
                        + URLEncoder.encode("DOB","UTF-8")+"="+ URLEncoder.encode(dob,"UTF-8")+"&"
                        + URLEncoder.encode("Height","UTF-8")+"="+ URLEncoder.encode(height,"UTF-8")+"&"
                        + URLEncoder.encode("Weight","UTF-8")+"="+ URLEncoder.encode(weight,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();

                editor.putString("flag","register");
                editor.commit();
                return "Successfully Registered " + firstName + " " + lastName;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(task.equals("login")){
            String FirstName= params[1];
            String LastName = params[2];
            try {
                URL url = new URL(urlLogin);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //send the email and password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("FirstName","UTF-8")+"="+ URLEncoder.encode(FirstName,"UTF-8")+"&"
                        + URLEncoder.encode("LastName","UTF-8")+"="+ URLEncoder.encode(LastName,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //get response from the database
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while((inputLine = bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(dataResponse);

                editor.putString("flag","login");
                editor.commit();
                return  dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (task.equals("record")){

            String result = "";
            //String b = params[1];
            String vc = params[1];
            String hr = params[2];
            String t = params[3];
            try {
                URL url = new URL(urlRecord);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setUseCaches(false);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setConnectTimeout(5000);

                OutputStream ops = http.getOutputStream();

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("verifyCode", "UTF-8") + "=" + URLEncoder.encode(vc, "UTF-8")
                        + "&&" + URLEncoder.encode("HeartRate", "UTF-8") + "=" + URLEncoder.encode(hr, "UTF-8")
                        + "&&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(t, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                int responseCode = http.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    reader.close();
                    ips.close();
                    http.disconnect();
                    return result;
                }

            } catch (MalformedURLException e) {
                result = e.getMessage();
            } catch (IOException e){
                result = e.getMessage();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //This method will be called when doInBackground completes... and it will return the completion string which
    //will display this toast.
    @Override
    protected void onPostExecute(String s) {
        String flag = preferences.getString("flag","0");

        if(flag.equals("register")) {
            Toast.makeText(context,s, Toast.LENGTH_LONG).show();
        }
        if(flag.equals("login")){
            String userId = "";
            String name = "";
            String last = "";
            String[] serverResponse =  s.trim().split("[,]");
            userId = serverResponse[0];
            name = serverResponse[1];
            last = serverResponse[2];

            if(!(s.trim()).equals("0")){
                editor.putString("PersonalID",userId);
                editor.commit();
                editor.putString("name",name +" "+ last);
                editor.commit();
                Intent intent = new Intent(context, HRActivity.class);
                context.startActivity(intent);
            }else{
                display("Login Failed...", "The name entered is not registered in the database");
            }
        }/*else{
            display("Server Connection Failure...","Please check with you administrator to ensure you are connected wih the server. ");
        }*/
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
