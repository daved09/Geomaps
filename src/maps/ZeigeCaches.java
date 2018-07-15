package maps;


import database.Cachedatabase;

/**
 * Created by hbfit14bannasch on 15.02.16.
 */
public class ZeigeCaches {
    Cachedatabase data = new Cachedatabase();
    private float[] lgrad;
    private float[] bgrad;

    public int laenge(){
        return lgrad.length;
    }

    public float zeigel(int i){
        float lgrad = this.lgrad[i];
        return lgrad;
    }
    public float zeigeb(int i){
        float bgrad = this.bgrad[i];
        return bgrad;
    }

    public void ladeCaches(String hostname, String hostport){
        try{
            data.listlgrad(hostname,hostport);
            lgrad = new float[data.getEnd()];
            data.listbgrad(hostname,hostport);
            bgrad = new float[data.getEnd()];
            for(int i=0;i<lgrad.length;i++){
                lgrad[i] = data.lgradl(i);
            }
            for(int i=0;i<bgrad.length;i++){
                bgrad[i] = data.bgradl(i);
            }
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
}
