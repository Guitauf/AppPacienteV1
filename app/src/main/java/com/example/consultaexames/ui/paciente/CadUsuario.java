package com.example.consultaexames.ui.paciente;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.consultaexames.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadUsuario extends Fragment implements View.OnClickListener, Response.ErrorListener, Response.Listener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View root;
    private EditText etNome;
    private EditText etCpf;
    private EditText etSenha;
    private CheckBox cbAceite;
    private Button btSalvar;
    private Spinner Sexo;
    //volley
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectReq;

    public CadUsuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CadUsuario.
     */
    // TODO: Rename and change types and number of parameters
    public static CadUsuario newInstance(String param1, String param2) {
        CadUsuario fragment = new CadUsuario();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_cad_usuario, container, false); //VERIFICAR

        // Inflate the layout for this fragment
        this.etNome = (EditText) root.findViewById(R.id.etNome);
        this.etCpf = (EditText) root.findViewById(R.id.etCPF);
        this.etSenha = (EditText) root.findViewById(R.id.etSenha);
        this.cbAceite = (CheckBox) root.findViewById(R.id.cbAceite);
        this.Sexo=(Spinner) root.findViewById(R.id.spinner);
        this.btSalvar=(Button) root.findViewById(R.id.btSalvar);
        this.btSalvar.setOnClickListener(this);
        //instanciando a fila de requests - caso o objeto seja o root
        this.requestQueue = Volley.newRequestQueue(root.getContext());

//inicializando a fila de requests do SO
        this.requestQueue.start();
        return root;
    }
    public void onClick(View view) {
        switch (view.getId()) {
//verificando se é o botão salvar
            case R.id.btSalvar:
//instanciando objeto de negócio
                Paciente u = new Paciente();
//populando objeto com dados da tela
                u.setNome(this.etNome.getText().toString());
                u.setCPF(this.etCpf.getText().toString()); //VERIFICAR
                u.setSenha(this.etSenha.getText().toString());
                u.setAceite(this.cbAceite.isChecked());
                u.setSexo(this.Sexo.getSelectedItemPosition());


                //enviar objeto para o REST Server
                jsonObjectReq = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://10.0.2.2:8080/exames/rest/paciente",
                        u.toJsonObject(), this, this);
                requestQueue.add(jsonObjectReq);
                break;

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Snackbar mensagem= Snackbar.make(root,
                "Houve um problema ao realizar cadastro:"+error.toString(),Snackbar.LENGTH_LONG);
        mensagem.show();
    }

    @Override
    public void onResponse(Object response) {
        String resposta = response.toString();
        try {
            if(resposta.equals("500")) {
                Snackbar mensagem = Snackbar.make(root,

                        "Erro! = " + resposta,
                        Snackbar.LENGTH_LONG);

                mensagem.show();
            } else {
//sucesso
//limpar campos da tela
                this.etNome.setText("");
                this.etCpf.setText("");
                this.etSenha.setText("");
                this.Sexo.setSelection(0);
//mensagem de sucesso
                Snackbar mensagem = Snackbar.make(root,
                        "Sucesso! = " + resposta,
                        Snackbar.LENGTH_LONG);

                mensagem.show();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    }

