package com.example.proyectofragmentos.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.proyectofragmentos.R;
import com.example.proyectofragmentos.adaptador.AdaptadorArchivo;
import com.example.proyectofragmentos.adaptador.Singleton;
import com.example.proyectofragmentos.clases.Estudiante;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarEstudiante#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarEstudiante extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText et_cedula;
    EditText et_apellido;
    EditText et_nombre;


    public EditarEstudiante() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarEstudiante.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarEstudiante newInstance(String param1, String param2) {
        EditarEstudiante fragment = new EditarEstudiante();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_editar_estudiante, container, false);

        et_cedula = rootView.findViewById(R.id.editTextCedula);
        et_apellido = rootView.findViewById(R.id.editTextApellido);
        et_nombre = rootView.findViewById(R.id.editTextNombre);
        ImageButton bt_guardar = rootView.findViewById(R.id.imageButtonOKEditarEstudiante);
        ImageButton bt_cancelar = rootView.findViewById(R.id.imageButtonCancelarEditarEstudiante);

        if(!mParam1.equals("")){
            final Estudiante estudiante = Singleton.getInstance().estudiantes.get(Integer.parseInt(mParam1));
            et_cedula.setText(estudiante.getCedula());
            et_apellido.setText(estudiante.getApellido());
            et_nombre.setText(estudiante.getNombre());

            bt_guardar.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    editarEstudiante(estudiante);
                    irAListaEstudiantes();
                }
            }));
        }else{
            et_cedula.setText("");
            et_apellido.setText("");
            et_nombre.setText("");

            bt_guardar.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    guardarEstudiante();
                    irAListaEstudiantes();
                }
            }));
        }

        bt_cancelar.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it){
                irAListaEstudiantes();
            }
        }));

        return rootView;
    }

    public void irAListaEstudiantes(){
        FragmentoEstudiantes fragmentoEstudiantes = FragmentoEstudiantes.newInstance("", "");
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.root_frame_est, fragmentoEstudiantes);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void guardarEstudiante(){
        String cedula = et_cedula.getText().toString();
        String apellido = et_apellido.getText().toString();
        String nombre = et_nombre.getText().toString();
        Estudiante nuevo_estudiante = new Estudiante(cedula, nombre, apellido);
        nuevo_estudiante.guardarEnArchivo();
        Singleton.getInstance().estudiantes.add(nuevo_estudiante);
    }

    public void editarEstudiante(Estudiante estudiante){
        estudiante.setCedula(et_cedula.getText().toString());
        estudiante.setApellido(et_apellido.getText().toString());
        estudiante.setNombre(et_nombre.getText().toString());
        new AdaptadorArchivo().eliminarArchivoMaterias();
    }

}
