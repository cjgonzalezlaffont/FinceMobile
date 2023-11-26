package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.fince.R
import com.example.fince.data.model.ObjetivoModel
import com.example.fince.databinding.FragmentObjetivoDialogBinding
import com.example.fince.listeners.OnEditObjetiveListener
import com.example.fince.ui.viewmodel.ObjetivoViewModel
import com.google.android.material.snackbar.Snackbar

class ObjetivoDialogFragment : DialogFragment() {

    private var _binding: FragmentObjetivoDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private var listener: OnEditObjetiveListener? = null
    private val objetivoViewModel: ObjetivoViewModel by viewModels({ requireParentFragment() })

    companion object {

        private lateinit var objetivo : ObjetivoModel

        // Método para crear una nueva instancia del fragmento con los detalles
        fun newInstance(objetivo : ObjetivoModel): ObjetivoDialogFragment {
            val fragment = ObjetivoDialogFragment()
            this.objetivo = objetivo
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentObjetivoDialogBinding.inflate(inflater, container, false)
        view = binding.root
        var activity = requireActivity()
        var viewActivity = activity.findViewById<View>(android.R.id.content)

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        val progresoInt = ((objetivo.progreso * 100).toInt())
        binding.fragObjDiagTextViewTitulo.setText(objetivo.nombre)
        binding.fragObjDiagTextViewDescripcion.setText(objetivo.descripcion)
        binding.fragObjDiagTextViewMonto.setText(objetivo.monto.toString())
        binding.fragObjDiagTextViewFecha.setText(objetivo.fecha)
        binding.fragObjProgressBarHorizontal.progress = progresoInt
        if (progresoInt == 100) {
            binding.fragObjTxtViewProgressLabel.text = "Completado!"
            binding.fragObjTxtViewProgressLabel.setTextColor((ContextCompat.getColor(requireContext(), R.color.green)))
        }

        binding.fragObjDiagBtnDelete.setOnClickListener {
            objetivoViewModel.deleteObjective(usuarioId, objetivo)
        }

        binding.fragObjDiagBtnEditar.setOnClickListener {
            listener?.onEditObjetive(objetivo)
            dismiss()
        }

        objetivoViewModel.errorData.observe(viewLifecycleOwner) { error ->
            if (error != "") {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }

        objetivoViewModel.responseData.observe(viewLifecycleOwner) { response ->
            if (response != ""){
                Snackbar.make(viewActivity, "Categoria eliminada con exito!", Snackbar.LENGTH_SHORT).show()
                dismiss()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        // Definir el ancho y alto fijo del diálogo
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Ajusta el alto como desees
    }

    fun setListener(listener : OnEditObjetiveListener){
        this.listener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        objetivoViewModel.clearError()
        objetivoViewModel.clearResponse()
        objetivoViewModel.errorData.removeObservers(viewLifecycleOwner)
        objetivoViewModel.responseData.removeObservers(viewLifecycleOwner)
        _binding = null
    }
}