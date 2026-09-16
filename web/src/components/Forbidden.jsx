import { motion } from "framer-motion";

export default function Forbidden() {
  return (
    <div className="min-h-screen bg-dev-bg text-dev-text flex flex-col items-center justify-center p-4">
      <motion.div
        initial={{ opacity: 0, y: 10 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.3, ease: "easeOut" }}
        className="text-center"
      >
        <div className="inline-block border border-dev-border bg-dev-card rounded-md px-3 py-1 text-xs font-mono font-medium text-dev-secondary mb-6 tracking-widest">
          403
        </div>

        <h1 className="text-xl font-semibold tracking-tight text-dev-text mb-2">
          Access Forbidden
        </h1>
        <p className="text-sm text-dev-secondary max-w-[260px] mx-auto">
          You do not have permission to access this resource.
        </p>
      </motion.div>
    </div>
  );
}
