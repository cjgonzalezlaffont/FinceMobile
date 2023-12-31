package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import com.example.fince.adapters.TransaccionAdapter
import com.example.fince.data.model.Transaccion
import com.example.fince.databinding.FragmentPresupuestoBinding
import com.example.fince.listeners.OnViewItemClickedListenerTran
import com.example.fince.ui.viewmodel.TransaccionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PresupuestoFragment : Fragment(), OnViewItemClickedListenerTran {

    private var _binding: FragmentPresupuestoBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    lateinit var recListTransacciones: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    var listaTransacciones : MutableList<Transaccion> = ArrayList()
    private val transaccionViewModel: TransaccionViewModel by viewModels()
    private lateinit var transaccionAdapter: TransaccionAdapter
    private var incomeAmount : Float = 0.toFloat()
    private var expenseAmount : Float = 0.toFloat()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPresupuestoBinding.inflate(inflater, container, false)
        view = binding.root
        recListTransacciones = binding.fragTranRecViewTransaccion

        return view

    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        transaccionViewModel.onCreate(usuarioId)

        requireActivity()
        recListTransacciones.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)
        transaccionAdapter = TransaccionAdapter(listaTransacciones, this)

        recListTransacciones.layoutManager = linearLayoutManager
        recListTransacciones.adapter = transaccionAdapter

        transaccionViewModel.transaccionList.observe(viewLifecycleOwner) {
            transaccionAdapter.setTransactionList(it)
            incomeAmount = transaccionViewModel.incomeAmount
            expenseAmount = transaccionViewModel.expenseAmount
            setMontos()
        }

        transaccionViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.isLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.fragTranBtnCat.setOnClickListener{
            val action = PresupuestoFragmentDirections.actionPresupuestoToCategoriaFragment()
            view.findNavController().navigate(action)
        }

        binding.fragTranBtnAddTran.setOnClickListener{
            val accion = PresupuestoFragmentDirections.actionPresupuestoToAgregarTranFragment()
            view.findNavController().navigate(accion)
        }
    }

    private fun setMontos(){
        binding.fragTranTvIncome.setText("$" + incomeAmount.toString())
        binding.fragTranTvExpense.setText("$" + expenseAmount.toString())
        binding.fragTranTvTotal.setText("$" + (incomeAmount - expenseAmount))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewItemDetail(transaccion: Transaccion) {
        val dialogFragment = TransaccionDialogFragment.newInstance(transaccion)
        dialogFragment.show(childFragmentManager, "detalle_dialog")
    }

}
