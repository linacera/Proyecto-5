package com.example.proyectofragmentos.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.proyectofragmentos.R;
import com.example.proyectofragmentos.adaptador.AdaptadorArchivo;
import com.example.proyectofragmentos.adaptador.Singleton;
import com.example.proyectofragmentos.clases.Materia;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarMateria#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarMateria extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText et_codigo;
    private EditText et_nombre;
    private EditText et_profesor;

    public EditarMateria() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarMateria.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarMateria newInstance(String param1, String param2) {
        EditarMateria fragment = new EditarMateria();
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
        View rootView =  inflater.inflate(R.layout.fragment_editar_materia, container, false);

        this.et_codigo = rootView.findViewById(R.id.editTextCodigoMateria);
        this.et_nombre = rootView.findViewById(R.id.editTextNombreMateria);
        this.et_profesor = rootView.findViewById(R.id.editTextProfesor);

        ImageButton guardar = rootView.findViewById(R.id.imageButtonOkEditarMateria);
        ImageButton cancelar = rootView.findViewById(R.id.imageButtonCancelarEditarMateria);

        if(!mParam1.equals("")){
            final Materia materia = Singleton.getInstance().materias.get(Integer.parseInt(mParam1));

            this.et_codigo.setText(materia.getCodigo());
            this.et_nombre.setText(materia.getNombre());
            this.et_profesor.setText(materia.getProfesor());

            guardar.setOnClickListener(new View.OnClickListener(){
                public final void onClick(View it){
                    editarMateria(materia);
                    regresarAMaterias();
                }
            });
        }else{
            this.et_codigo.setText("");
            this.et_nombre.setText("");
            this.et_profesor.setText("");

            guardar.setOnClickListener(new View.OnClickListener(){
                public final void onClick(View it){
                    guardarMateria();
                    regresarAMaterias();
                }
            });
        }

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarAMaterias();
            }
        });

        return rootView;
    }

    public void editarMateria(Materia materia){
        materia.setCodigo(this.et_codigo.getText().toString());
        materia.setNombre(this.et_nombre.getText().toString());
        materia.setProfesor(this.et_profesor.getText().toString());
        new AdaptadorArchivo().eliminarArchivoMaterias();
    }

    public void guardarMateria(){
        String codigo = this.et_codigo.getText().toString();
        String nombre = this.et_nombre.getText().toString();
        String profesor = this.et_profesor.getText().toString();
        Materia nuevaMateria = new Materia(codigo,nombre,profesor);
        nuevaMateria.guardarEnArchivo();
        Singleton.getInstance().materias.add(nuevaMateria);
    }

    public void regresarAMaterias(){
        FragmentoMaterias fragmentoMaterias = FragmentoMaterias.newInstance("", "");
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.root_frame_mat, fragmentoMaterias);
        fragmentTransaction.addToBackStack(null).commit();
    }

}
